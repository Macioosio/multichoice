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
        <md-table-cell md-label="Start" md-sort-by="item.start">{{convertDate(item.start)}}</md-table-cell>
        <!--<md-table-cell>
          <router-link class="button" :to="'/questions/edit/' + item.id">Edytuj</router-link>
        </md-table-cell>-->
      </md-table-row>
    </md-table>
  </div>
</template>

<script>
import axios from 'axios'
import $store from '../store/store'
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
        .get('/api/tests', $store.getters.getAuthHeader)
        .then(response => (this.tests = response.data))
    },
    convertDate (dateJavaFormat) {
      // 2018-10-19T10:00:00.628+0000
      let year = dateJavaFormat.substring(0, 10)
      let time = dateJavaFormat.substring(11, 19)
      return year + ' ' + time
    }
  }

}
</script>

<style scoped>
  .padding-class {
    padding: 25px;
  }
</style>
