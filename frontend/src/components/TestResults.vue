<template>
  <div class="container padding-class">
    <div class="md-layout md-gutter">
      <div class="md-layout-item md-size-20">
        <div class="small-padding-class">
          <label class="label">Kurs</label>
          <p class="card">
            {{course.name}}</p>
        </div>
        <div class="small-padding-class">
          <label class="label">Start</label>
          <div class="card">
          {{start}}
          </div>
          <label class="label">Koniec</label>
          <div class="card">
          {{end}}
          </div>
        </div>
        <div class="small-padding-class">
          <label class="label">Metoda oceniania</label>
          <div class="card">{{gradingMethod}}</div>
        </div>
        <label class="label">Nawigowalność</label>
        <div>
          <div class="card"> {{navigable}}</div>
        </div>
        <div class="small-padding-class">
          <label class="label">Punkty</label>
          <div class="card">{{points}}</div>
        </div>
        <div class="small-padding-class">
          <router-link class="button" to="/tests">Wróć</router-link>
        </div>
      </div>
      <div class="md-layout-item md-size-80">
        <md-table v-model="solutions" md-card md-fixed-header>
          <md-table-toolbar>
            <h1 class="md-title md-toolbar-section-start">Rozwiązania</h1>
            <h2 md-title md-toolbar-section-end>Średnia: {{meanPoints}} punktów</h2>
          </md-table-toolbar>
          <md-table-row slot="md-table-row" slot-scope="{ item }">
            <md-table-cell md-label="Autor">{{ item.author.email }}</md-table-cell>
            <md-table-cell md-label="Punkty">{{item.points}}</md-table-cell>
            <md-table-cell md-label="Procenty">{{calculatePercent(item.points)}}%</md-table-cell>
            <md-table-cell md-label="Przesłane">{{stringify(item.posted)}}</md-table-cell>
          </md-table-row>
        </md-table>
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
      meanPoints: 0,
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
      let sumPoints = data.map(solution => solution.points).reduce((p, c) => p + c, 0)
      this.meanPoints = sumPoints / data.length
    },
    convertDate (dateJavaFormat) {
      // 2018-10-19T10:00:00.628+0000
      return moment(dateJavaFormat).format('YYYY-MM-DD HH:mm')
    },
    calculatePercent (points) {
      return (points / this.points) * 100
    },
    stringify (boolean) {
      return boolean ? 'Tak' : 'Nie'
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
