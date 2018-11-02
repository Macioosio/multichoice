<template>
  <div class="container padding-class">
    <md-table v-model="tests" md-card md-fixed-header>
      <md-table-toolbar>
        <h1 class="md-title md-toolbar-section-start">Testy</h1>
      </md-table-toolbar>
      <md-table-row slot="md-table-row" slot-scope="{ item }">
        <md-table-cell md-label="Przedmiot">{{ item.course.name }}</md-table-cell>
        <md-table-cell md-label="Start" md-sort-by="start">{{convertDate(item.start)}}</md-table-cell>
        <md-table-cell md-label="Koniec" md-sort-by="end">{{convertDate(item.end)}}</md-table-cell>
        <md-table-cell width="50">
          <button class="button" :disabled="!isSolvingTime(item)" v-on:click="routeToTestSolving(item)">Rozwiąż</button>
        </md-table-cell>
      </md-table-row>
    </md-table>
  </div>
</template>

<script>
// TODO Conditionally change "rozwiąż" button to test result.
import axios from 'axios'
import * as moment from 'moment'
import {router} from '../router/index'

export default {
  name: 'StudentsTests',
  data () {
    return {
      tests: []
    }
  },
  mounted () {
    this.fetchStudentsTests()
  },
  methods: {
    fetchStudentsTests () {
      axios
        .get('/api/students/tests/mine', {headers: {'Authorization': sessionStorage.getItem('user-token')}})
        .then(response => (this.tests = response.data))
    },
    convertDate (dateJavaFormat) {
      // 2018-10-19T10:00:00.628+0000
      return moment(dateJavaFormat).format('YYYY-MM-DD HH:mm')
    },
    isSolvingTime (test) {
      return moment().isBetween(test.start, test.end)
    },
    routeToTestSolving (test) {
      router.push('/test/solving/' + test.id)
    }
  }
}
</script>

<style scoped>
  .padding-class {
    padding: 25px;
  }
</style>
