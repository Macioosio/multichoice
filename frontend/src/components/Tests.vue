<template>
  <div class="container padding-class">
    <md-table v-model="tests" md-card md-fixed-header>
      <md-table-toolbar>
        <h1 class="md-title md-toolbar-section-start">Testy</h1>
        <router-link class="button" :to="'/tests/add'">Dodaj</router-link>
      </md-table-toolbar>
      <md-table-row slot="md-table-row" slot-scope="{ item }">
        <md-table-cell md-label="Test" width="400">{{ item.id }}</md-table-cell>
        <md-table-cell md-label="Przedmiot">{{ item.course.name }}</md-table-cell>
        <md-table-cell md-label="Start">{{convertDate(item.start)}}</md-table-cell>
        <md-table-cell md-label="Koniec">{{convertDate(item.end)}}</md-table-cell>
        <md-table-cell width="50">
          <router-link class="button" :to="'/tests/edit/' + item.id">Edytuj</router-link>
        </md-table-cell>
        <md-table-cell width="50">
          <router-link class="button" :to="'/test/results/' + item.id">Wyniki</router-link>
        </md-table-cell>
      </md-table-row>
    </md-table>
  </div>
</template>

<script>
import axios from 'axios'
import * as moment from 'moment'

export default {
  name: 'Tests',
  data () {
    return {
      tests: []
    }
  },
  mounted () {
    this.fetchData()
  },
  methods: {
    fetchData () {
      axios
        .get('/api/tests', {headers: {'Authorization': sessionStorage.getItem('user-token')}})
        .then(response => (this.sortTests(response.data)))
    },
    sortTests (tests) {
      this.tests = tests.sort((a, b) => (a.start < b.start) ? 1 : ((b.start > a.start) ? -1 : 0))
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
</style>
