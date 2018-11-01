<template>
  <div class="container padding-class">
    <div class="md-layout md-gutter">
      <div class="md-layout-item">
        {{solution}}
      </div>
      <div class="md-layout-item md-size-50">
        <div class="box is-large">
          {{questions[0].content}}
        </div>
        <div class="small-padding-class" v-for="answer in questions[0].answers" v-bind:key="answer.id">
          <div class="button is-fullwidth is-medium"
               v-on:click="answer.selected = answer.selected? false : true"
               v-bind:class="{ 'is-success' : answer.selected}">
            {{answer.content}}
          </div>
        </div>
      </div>
      <div class="md-layout-item">
        {{questions}}
      </div>
    </div>
  </div>
</template>

<script>
// TODO activequestion -> at first the first button next changes to the next question. on last question this button is not visible
import axios from 'axios'
import $store from '../store/store'
export default {
  name: 'TestSolving',
  props: ['testId'],
  data () {
    return {
      questions: {},
      activeQuestion: {},
      solution: {}
    }
  },
  mounted () {
    this.fetchQuestions()
    this.createOrGetSolution()
  },
  methods: {
    fetchQuestions () {
      axios
        .get('/api/tests/' + this.testId + '/questions', $store.getters.getAuthHeader)
        .then(response => (this.questions = this.handleQuestions(response.data)))
    },
    handleQuestions (questions) {
      questions.forEach(
        question => question.answers.forEach(answer => (answer.selected = false))
      )
      return questions
    },
    createOrGetSolution () {
      let testId = this.testId
      let testIdForm = { testId }
      axios
        .post('/api/solutions/', testIdForm, $store.getters.getAuthHeader)
        .then(response => (this.solution = response.data))
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
