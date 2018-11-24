<template>
  <div class="container padding-class">
    <md-table v-model="tests" md-card md-fixed-header>
      <md-table-toolbar>
        <h1 class="md-title md-toolbar-section-start">Testy</h1>
      </md-table-toolbar>
      <md-table-row slot="md-table-row" slot-scope="{ item }">
        <md-table-cell md-label="Przedmiot">{{ item.course.name }}</md-table-cell>
        <md-table-cell md-label="Start">{{convertDate(item.start)}}</md-table-cell>
        <md-table-cell md-label="Koniec">{{convertDate(item.end)}}</md-table-cell>
        <md-table-cell width="50">
          <button class="button" v-if="!isAfterSolvingTime(item)"
                  :disabled="!isSolvingTime(item)" v-on:click="routeToTestSolving(item)">
            Rozwiąż
          </button>
          <button class="button" v-if="isAfterSolvingTime(item)" v-on:click="routeToTestSolving(item)">
            Wynik
          </button>
        </md-table-cell>
      </md-table-row>
    </md-table>
  </div>
</template>

<script>
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
        .then(response => (this.sortTests(response.data)))
    },
    sortTests (tests) {
      this.tests = tests.sort((a, b) => (a.start < b.start) ? 1 : ((b.start > a.start) ? -1 : 0))
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
    },
    isAfterSolvingTime (test) {
      return moment().isAfter(test.end)
    }
  }
}
</script>

<style scoped>
  .padding-class {
    padding: 25px;
  }
</style>
