<template>
  <div class="container padding-class">
    <div class="md-layout md-gutter" v-if="!isAuthorized">
      <div class="md-layout-item" >
      </div>
      <div class="md-layout-item">
        <form class="solve" @submit.prevent="startTest" v-if="!solution.posted">
          <div v-if="isSolvingTime">
            <div class="box">
              <p><b>Nawigacja po pytaniach</b> może przebiegać za pomocą przycisków zawierających numery pytań znajdujących się po lewej stronie
              lub za pomocą przycisku "Następne pytanie" znajdującego się po prawej stronie.</p>
              <P><b>Przycisk "Następne pytanie"</b> zapisuje również wybrane odpowiedzi.</P>
              <p>
                <b>Przycisk "Zapisz odpowiedź"</b> zapisuje odpowiedzi bez nawigacji do kolejnego pytania.
              </p>
              <p><b>Zakończenie rozwiązywania testu</b> odbywa się za pomocą przycisku "Zakończ test"</p>
            </div>
          <input class="input input-width" v-model="password" type="password" placeholder="Password"/>
          <button class="button" type="submit">Zacznij rozwiązywać</button>
          </div>
          <div v-if="!isSolvingTime" class="box" style="font-size: medium">
            <p>
              Nie nadesłano rozwiązania
            </p>
          </div>
        </form>
        <div class="box" style="font-size: medium" v-if="solution.posted">
          <p>
          Twoje rozwiązanie zostało już przesłane
          </p>
          Twój wynik to:
          <hr>
          {{solution.points}} / {{maxPoints}}
          <hr>
          {{percent}}%
        </div>
      </div>
      <div class="md-layout-item">
      </div>
    </div>
    <div class="md-layout md-gutter" v-if="isAuthorized">
      <div class="md-layout-item">
        <div class="box buttons has-addons">
          <button v-for="(question, index) in questions" :key="index"
                  class="button is-selected"
                  v-on:click="navigateToQuestion(question)"
                  v-bind:class="successIfActive(question)"
                  :disabled="isAnswered(question)"
          >
            {{index + 1}}
          </button>
        </div>
        <button class="button is-danger" v-on:click="confirmTestEnd">Zakończ test</button>
      </div>
      <div class="md-layout-item md-size-50">
        <div class="box is-large">
          {{activeQuestion.content}}
        </div>
        <div class="small-padding-class" v-for="answer in activeQuestion.answers" v-bind:key="answer.id">
          <div class="button is-fullwidth is-medium"
               v-on:click="answer.selected = answer.selected? false : true"
               v-bind:class="{ 'is-success' : answer.selected}">
            {{answer.content}}
          </div>
        </div>
      </div>
      <div class="md-layout-item">
        <div class="box buttons center">
          <button class="button" v-on:click="addAnswersToSolution">Zapisz odpowiedź</button>
          <button class="button" v-on:click="handleNextQuestion" :disabled="this.answeredQuestions.size === this.questions.length -1">Kolejne pytanie</button>
        </div>
      </div>
    </div>
    <md-snackbar :md-position="snackBarPosition" :md-duration="snackBarDuration" :md-active.sync="showSnackbar" md-persistent>
      <span>Wprowadzone hasło nie jest poprawne</span>
    </md-snackbar>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'TestSolving',
  props: ['testId'],
  data () {
    return {
      isSolvingTime: false,
      isAuthorized: false,
      password: '',
      questions: {},
      answeredQuestions: new Set(),
      activeQuestion: {},
      solution: {},
      maxPoints: 0,
      percent: 0,
      navigable: true,
      showSnackbar: false,
      snackBarDuration: 10000,
      snackBarPosition: 'center'
    }
  },
  mounted () {
    this.createOrGetSolution()
    this.prepareQuestions()
  },
  methods: {
    createOrGetSolution () {
      let testId = this.testId
      let testIdForm = { testId }
      axios
        .post('/api/solutions/', testIdForm, {headers: {'Authorization': sessionStorage.getItem('user-token')}})
        .then(response => this.handlePostedSolution(response.data))
    },
    handlePostedSolution (data) {
      this.solution = data
      axios
        .get('/api/solutions/' + this.solution.solutionId + '/grade', {headers: {'Authorization': sessionStorage.getItem('user-token')}})
        .then(response => this.setSolvedSolutionData(response.data))
    },
    setSolvedSolutionData (gradeDto) {
      this.maxPoints = gradeDto.maxPoints
      this.percent = gradeDto.percent
    },
    prepareQuestions () {
      axios
        .get('/api/tests/' + this.testId + '/solve', {headers: {'Authorization': sessionStorage.getItem('user-token')}})
        .then(response => (this.questions = this.handleQuestions(response.data)))
    },
    handleQuestions (solvingDto) {
      this.navigable = solvingDto.navigable
      this.isSolvingTime = solvingDto.solvingTime
      let questions = solvingDto.questions
      questions.forEach(
        question => question.answers.forEach(answer => (answer.selected = false))
      )
      this.activeQuestion = questions[0]
      return questions
    },
    startTest () {
      if (this.password === '') {
        this.showSnackbar = true
      } else {
        axios
          .get('/api/tests/' + this.testId + '/' + this.password + '/authorize', {headers: {'Authorization': sessionStorage.getItem('user-token')}})
          .then(() => this.startTestPositive())
          .catch(() => (this.showSnackbar = true))
      }
    },
    startTestPositive () {
      this.password = ''
      this.setSelectedAnswers()
      if (!this.navigable) {
        this.activeQuestion = this.questions.find(q => !this.isAnswered(q))
      }
      this.isAuthorized = true
    },
    setSelectedAnswers () {
      let selectedAnswers = this.solution.answers
      for (let question of this.questions) {
        for (let answer of question.answers) {
          let answerIsSelected = selectedAnswers.filter(ans => ans.id === answer.id).length > 0
          if (answerIsSelected) {
            answer.selected = true
            if (!this.navigable) {
              this.answeredQuestions.add(question)
            }
          }
        }
      }
    },
    handleNextQuestion () {
      this.addToAnsweredQuestions()
      this.addAnswersToSolution()
      this.navigateToNextQuestion()
    },
    addToAnsweredQuestions () {
      if (!this.navigable) {
        this.answeredQuestions.add(this.activeQuestion)
      }
    },
    addAnswersToSolution () {
      let selectedAnswersIds = this.activeQuestion.answers
        .filter(answer => answer.selected)
        .map(answer => answer.id)
      let transferData = {
        'questionID': this.activeQuestion.id,
        'answerIDs': selectedAnswersIds
      }
      axios
        .patch(
          '/api/solutions/' + this.solution.solutionId + '/add/answers',
          transferData,
          {headers: {'Authorization': sessionStorage.getItem('user-token')}}
        )
    },
    navigateToNextQuestion () {
      let activeQuestionIndex = this.questions.findIndex(question => question.id === this.activeQuestion.id)
      if (activeQuestionIndex === this.questions.length - 1) {
        activeQuestionIndex = -1
      }
      this.activeQuestion = this.questions[activeQuestionIndex + 1]
      if (this.isAnswered(this.activeQuestion)) {
        this.navigateToNextQuestion()
      }
    },
    navigateToQuestion (question) {
      this.addAnswersToSolution()
      this.addToAnsweredQuestions()
      this.activeQuestion = question
    },
    successIfActive (question) {
      let result = ''
      if (question.id === this.activeQuestion.id) {
        result = 'is-info'
      }
      return result
    },
    confirmTestEnd () {
      this.addAnswersToSolution()
      if (confirm('Czy na pewno chcesz zakończyć test?\nNiezapisane odpowiedzi nie będą brane pod uwagę')) {
        axios
          .patch('/api/solutions/' + this.solution.solutionId + '/post', {}, {headers: {'Authorization': sessionStorage.getItem('user-token')}})
          .then((response) => this.handleTestEnd(response.data))
      }
    },
    handleTestEnd (gradeDto) {
      this.isAuthorized = false
      this.solution.posted = true
      this.solution.points = gradeDto.points
      this.maxPoints = gradeDto.maxPoints
      this.percent = (gradeDto.points / gradeDto.maxPoints) * 100
    },
    isAnswered (question) {
      return this.answeredQuestions.has(question)
    }
  }
}
</script>

<style scoped>
  .padding-class {
    padding: 25px;
  }
  .small-padding-class {
    padding: 10px;
  }
</style>
