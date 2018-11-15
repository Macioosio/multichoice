<template>
  <div class="container padding-class">
    <div class="md-layout md-gutter">
      <div class="md-layout-item md-size-20">
        <div class="small-padding-class">
          <label class="label">Kurs</label>
          {{course}}
        </div>
        <div class="small-padding-class">
          <label class="label">Start</label>
          {{start}}
          <label class="label">Koniec</label>
          {{end}}
        </div>
        <div class="small-padding-class">
          <label class="label">Metoda oceniania</label>
          {{gradingMethod}}
        </div>
        <label class="label">Nawigowalność</label>
        <div>
          {{navigable}}
        </div>
        <div class="small-padding-class">
          <label class="label">Punkty</label>
          {{points}}
        </div>
        <div class="small-padding-class">
          <router-link class="button" to="/tests">Wróć</router-link>
        </div>
      </div>
      <div class="md-layout-item">
      </div>
      <div class="md-layout-item">
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import * as moment from 'moment'

export default {
  name: 'TestResults',
  props: ['testId'],
  data () {
    return {
      course: {},
      start: {},
      end: {},
      gradingMethod: '',
      navigable: true,
      points: 0,
      questions: [],
      students: [],
      solutions: []
    }
  },
  mounted () {
    this.fetchData()
  },
  methods: {
    fetchData () {
      this.fetchTest()
      axios
        .get('/api/tests/' + this.testId + '/solutions', {headers: {'Authorization': sessionStorage.getItem('user-token')}})
        .then(response => this.setSolutionsData(response.data))
    },
    fetchTest () {
      axios
        .get('/api/tests/' + this.testId, {headers: {'Authorization': sessionStorage.getItem('user-token')}})
        .then(response => this.setTestData(response.data))
    },
    setTestData (data) {
      this.course = data.course
      this.start = this.convertDate(data.start)
      this.end = this.convertDate(data.end)
      this.gradingMethod = data.gradingMethod
      this.navigable = data.navigable
      this.points = data.points
      this.questions = data.questions
      this.students = data.students
    },
    setSolutionsData (data) {
      this.solutions = data
    },
    convertDate (dateJavaFormat) {
      // 2018-10-19T10:00:00.628+0000
      return moment(dateJavaFormat).format('YYYY-MM-DD HH:mm')
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
