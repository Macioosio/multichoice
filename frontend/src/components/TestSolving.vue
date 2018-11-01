<template>
  <div class="container padding-class">
    <div class="md-layout md-gutter" v-if="!isAuthorized">
      <div class="md-layout-item">
      </div>
      <div class="md-layout-item">
        <button class="button" v-on:click="startTest">Zacznij rozwiązywać</button>
      </div>
      <div class="md-layout-item">
      </div>
    </div>
    <div class="md-layout md-gutter" v-if="isAuthorized">
      <div class="md-layout-item">
        <div class="box buttons has-addons">
          <button v-for="(question, index) in questions" :key="index"
                  class="button is-selected"
                  v-on:click="navigateToQuestion(question)" v-bind:class="successIfActive(question)">
            {{index + 1}}
          </button>
        </div>
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
        <button class="button" v-on:click="handleNextQuestion">Kolejne pytanie</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import $store from '../store/store'
export default {
  name: 'TestSolving',
  props: ['testId'],
  data () {
    return {
      isAuthorized: false,
      questions: {},
      activeQuestion: {},
      solution: {}
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
        .post('/api/solutions/', testIdForm, $store.getters.getAuthHeader)
        .then(response => (this.solution = response.data))
    },
    prepareQuestions () {
      axios
        .get('/api/tests/' + this.testId + '/questions', $store.getters.getAuthHeader)
        .then(response => (this.questions = this.handleQuestions(response.data)))
    },
    handleQuestions (questions) {
      questions.forEach(
        question => question.answers.forEach(answer => (answer.selected = false))
      )
      this.activeQuestion = questions[0]
      return questions
    },
    startTest () {
      this.isAuthorized = true
      this.setSelectedAnswers()
    },
    setSelectedAnswers () {
      let selectedAnswers = this.solution.answers
      for (let question of this.questions) {
        for (let answer of question.answers) {
          let answerIsSelected = selectedAnswers.filter(ans => ans.id === answer.id).length > 0
          if (answerIsSelected) {
            answer.selected = true
          }
        }
      }
    },
    handleNextQuestion () {
      this.addAnswersToSolution()
      this.navigateToNextQuestion()
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
          $store.getters.getAuthHeader
        )
    },
    navigateToNextQuestion () {
      let activeQuestionIndex = this.questions.findIndex(question => question.id === this.activeQuestion.id)
      if (activeQuestionIndex === this.questions.length - 1) {
        activeQuestionIndex = -1
      }
      this.activeQuestion = this.questions[activeQuestionIndex + 1]
    },
    navigateToQuestion (question) {
      this.addAnswersToSolution()
      this.activeQuestion = question
    },
    successIfActive (question) {
      let result = ''
      if (question.id === this.activeQuestion.id) {
        result = 'is-info'
      }
      return result
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
