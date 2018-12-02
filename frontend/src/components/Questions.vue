<template>
<div class="container padding-class">
  <h2 v-if="areaId" class = "subtitle">Pytania
    <span v-if="!isEditMode">z działu {{areaName}}</span>
    <span v-if="isEditMode">działu <input v-model="areaName" placeholder="this.course.name"/></span>
    <p align="right">
    <button class="button" v-if="!isEditMode" v-on:click="turnOnEditMode">Edytuj</button>
    <button class="button" v-if="!isEditMode" v-on:click="verifyDeleting">Usuń</button>
      <button class="button" v-if="isEditMode" v-on:click="saveChanges">Zapisz</button>
      <button class="button" v-if="isEditMode" v-on:click="abandonChanges">Wróć</button>
    </p>
  </h2>
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
import $store from '../store/store'
import {router} from '../router'

export default {
  name: 'Questions',
  props: ['courseId', 'areaId', 'questionId'],
  data () {
    return {
      questions: [],
      isEditMode: false,
      areaName: ''
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
        .get('/api/questions/', {headers: {'Authorization': sessionStorage.getItem('user-token')}})
        .then(response => (this.handleData(response.data)))
    },
    fetchCourseQuestions () {
      axios
        .get('/api/courses/' + this.courseId + '/questions', {headers: {'Authorization': sessionStorage.getItem('user-token')}})
        .then(response => (this.handleData(response.data)))
    },
    fetchAreaQuestions () {
      this.fetchAreaName()
      axios
        .get('/api/areas/' + this.areaId + '/questions', {headers: {'Authorization': sessionStorage.getItem('user-token')}})
        .then(response => (this.handleData(response.data)))
    },
    fetchAreaName () {
      axios
        .get('/api/areas/' + this.areaId, {headers: {'Authorization': sessionStorage.getItem('user-token')}})
        .then((response) => (this.areaName = response.data.name))
    },
    handleData (questions) {
      if (this.questionId) {
        this.questions = questions.filter(c => { return c.id !== this.questionId })
        this.clearStoredAnswers()
        axios
          .delete('/api/questions/' + this.questionId, {headers: {'Authorization': sessionStorage.getItem('user-token')}})
      } else {
        this.questions = questions
      }
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
    },
    clearStoredAnswers () {
      $store.state.answers = [
        {
          id: '',
          content: '',
          correct: false
        },
        {
          id: '',
          content: '',
          correct: false
        },
        {
          id: '',
          content: '',
          correct: false
        },
        {
          id: '',
          content: '',
          correct: false
        }
      ]
    },
    turnOnEditMode () {
      this.isEditMode = true
    },
    verifyDeleting () {
      if (confirm('Czy na pewno chcesz usunąć ten dział?')) {
        router.push('/courses')
      }
    },
    abandonChanges () {
      this.isEditMode = false
    },
    saveChanges () {
      this.isEditMode = false
    }
  }
}
</script>

<style scoped>
  .padding-class {
    padding: 25px;
  }
</style>
