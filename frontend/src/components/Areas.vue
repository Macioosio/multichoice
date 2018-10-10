<template>
<div class="container">
  <div class="md-layout md-gutter">
    <div class="md-layout-item">
      <router-link class="button" :to="'/course/' + courseId + '/questions'">Pokaż pytania</router-link>
      <router-link class="button" :to="'/questions/add'">Dodaj pytanie</router-link>
    </div>
    <div class="md-layout-item">
      <h2 class = "subtitle">Działy
        <span v-if="!isEditMode">kursu {{course.name}}</span>
        <span v-if="isEditMode">kursu <input v-model="course.name" placeholder="this.course.name"/></span>
      </h2>
      <!--break-->
      <input class="input" v-if="isAddMode" v-model="area.name" placeholder="Nazwa działu..."/>
      <button class="button" v-if="isAddMode" v-on:click="saveAdding">Zapisz</button>
      <button class="button" v-if="isAddMode" v-on:click="abandonChanges">Wróć</button>
      <!--break-->
      <md-list>
        <md-list-item v-if="!isAddMode"
                      v-for ="area in areas" v-bind:key="area.id"
                      :to="'/area/' + area.id +'/questions/'">
          {{area.name}}
        </md-list-item>
      </md-list>
    </div>
    <div class="md-layout-item">
      <button class="button" v-if="!isEditMode && !isAddMode" v-on:click="turnOnAddMode">Dodaj dział</button>
      <button class="button" v-if="!isEditMode && !isAddMode" v-on:click="turnOnEditMode">Edytuj</button>
      <button class="button" v-if="isEditMode" v-on:click="saveChanges">Zapisz</button>
      <button class="button" v-if="isEditMode" v-on:click="abandonChanges">Wróć</button>
      <router-link class="button" v-if="!isAddMode" :to="'/courses/delete/' + courseId">Usuń</router-link>
    </div>
  </div>
</div>
</template>

<script>
import axios from 'axios'

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
        .get('/api/courses/' + this.courseId + '/areas')
        .then(response => (this.areas = response.data))
    },
    fetchCourse () {
      axios
        .get('/api/courses/' + this.courseId)
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
        .put('/api/courses/', this.course)
      this.isEditMode = false
    },
    saveAdding () {
      this.area.course = this.course
      axios
        .post('api/areas', this.area)
        // .then(() => this.areas.push(this.area))
        .then(() => this.fetchCourseAreas())
      this.isAddMode = false
    }
  }
}
</script>

<style scoped>

</style>
