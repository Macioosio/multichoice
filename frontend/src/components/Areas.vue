<template>
<div class="container">
  <div class="md-layout md-gutter">
    <div class="md-layout-item padding-class">
      <router-link class="button" :to="'/course/' + courseId + '/questions'">Pokaż pytania</router-link>
      <router-link class="button" :to="'/course/' + courseId + '/questions/add'">Dodaj pytanie</router-link>
    </div>
    <div class="md-layout-item padding-class">
      <h2 class = "subtitle">Działy
        <span v-if="!isEditMode">kursu {{course.name}}</span>
        <span v-if="isEditMode">kursu <input v-model="course.name" placeholder="this.course.name"/></span>
      </h2>
      <md-list v-if="!isAddMode">
        <md-list-item v-if="!isAddMode"
                      v-for ="area in areas" v-bind:key="area.id"
                      :to="'/area/' + area.id +'/questions/'">
          {{area.name}}
        </md-list-item>
      </md-list>
      <!--break-->
      <input class="input" v-if="isAddMode" v-model="area.name" placeholder="Nazwa działu..."/>
      <button class="button" v-if="isAddMode" v-on:click="saveAdding">Zapisz</button>
      <button class="button" v-if="isAddMode" v-on:click="abandonChanges">Wróć</button>
      <!--break-->
    </div>
    <div class="md-layout-item padding-class">
      <button class="button" v-if="!isEditMode && !isAddMode" v-on:click="turnOnAddMode">Dodaj dział</button>
      <button class="button" v-if="!isEditMode && !isAddMode" v-on:click="turnOnEditMode">Edytuj</button>
      <button class="button" v-if="isEditMode" v-on:click="saveChanges">Zapisz</button>
      <button class="button" v-if="isEditMode" v-on:click="abandonChanges">Wróć</button>
      <button class="button" v-if="!isAddMode" v-on:click="verifyDeleting">Usuń</button>
    </div>
  </div>
</div>
</template>

<script>
import axios from 'axios'
import {router} from '../router/index'

export default {
  name: 'Areas',
  props: ['courseId'],
  data () {
    return {
      areas: [],
      course: {},
      isEditMode: false,
      isAddMode: false,
      area: {
        'course': {},
        'name': ''
      }
    }
  },
  mounted () {
    this.fetchCourseAreas()
    this.fetchCourse()
  },
  methods: {
    fetchCourseAreas () {
      axios
        .get('/api/courses/' + this.courseId + '/areas', {headers: {'Authorization': sessionStorage.getItem('user-token')}})
        .then(response => (this.areas = response.data))
    },
    fetchCourse () {
      axios
        .get('/api/courses/' + this.courseId, {headers: {'Authorization': sessionStorage.getItem('user-token')}})
        .then(response => (this.course = response.data))
    },
    turnOnEditMode () {
      this.isEditMode = true
    },
    turnOnAddMode () {
      this.isAddMode = true
    },
    abandonChanges () {
      this.isEditMode = false
      this.isAddMode = false
    },
    saveChanges () {
      axios
        .put('/api/courses/', this.course, {headers: {'Authorization': sessionStorage.getItem('user-token')}})
      this.isEditMode = false
    },
    saveAdding () {
      this.area.course = this.course
      axios
        .post('api/areas', this.area, {headers: {'Authorization': sessionStorage.getItem('user-token')}})
        // .then(() => this.areas.push(this.area))
        .then(() => this.fetchCourseAreas())
      this.isAddMode = false
    },
    verifyDeleting () {
      if (confirm('Czy na pewno chcesz usunąć ten kurs?')) {
        router.push('/courses/delete/' + this.courseId)
      }
    }
  }
}
</script>

<style scoped>
  .padding-class {
    padding: 25px;
  }
</style>
