<template>
<div class="container padding-class">
  <md-table v-model="questions" md-card md-fixed-header>
    <md-table-toolbar>
      <h1 class="md-title md-toolbar-section-start">Pytania</h1>
      <router-link class="button" :to="getRoute()">Dodaj</router-link>
    </md-table-toolbar>
    <md-table-row slot="md-table-row" slot-scope="{ item }">
      <md-table-cell md-label="Pytanie" md-sort-by="content" width="800">{{ item.content }}</md-table-cell>
      <md-table-cell md-label="Przedmiot" md-sort-by="item.area.course.name">{{ item.area.course.name }}</md-table-cell>
      <md-table-cell md-label="Dział" md-sort-by="item.area">{{ item.area.name }}</md-table-cell>
      <md-table-cell>
        <router-link class="button" :to="'/questions/edit/' + item.id">Edytuj</router-link>
      </md-table-cell>
    </md-table-row>
  </md-table>
</div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'Questions',
  props: ['courseId', 'areaId'],
  data () {
    return {
      questions: []
    }
  },
  mounted () {
    this.fetchData()
  },
  methods: {
    fetchData () {
      if (this.courseId) {
        this.fetchCourseQuestions()
      } else {
        if (this.areaId) {
          this.fetchAreaQuestions()
        } else {
          this.fetchAllQuestions()
        }
      }
    },
    fetchAllQuestions () {
      axios
        .get('/api/questions')
        .then(response => (this.questions = response.data))
    },
    fetchCourseQuestions () {
      axios
        .get('/api/courses/' + this.courseId + '/questions')
        .then(response => (this.questions = response.data))
    },
    fetchAreaQuestions () {
      axios
        .get('/api/areas/' + this.areaId + '/questions')
        .then(response => (this.questions = response.data))
    },
    getRoute () {
      let route = '/questions/add'
      if (this.areaId) {
        route = '/area/' + this.areaId + route
      }
      if (this.courseId) {
        route = '/course/' + this.courseId + route
      }
      return route
    }
  }
}
</script>

<style scoped>
  .padding-class {
    padding: 25px;
  }
</style>
