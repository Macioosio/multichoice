<template>
<div class="container">
  <h2>Działy</h2>
  <p v-if="!isEditMode">{{course.name}}</p>
  <p>
    <input v-if="isEditMode" v-model="course.name" placeholder="this.course.name"/>
    <button v-if="!isEditMode && !isAddMode" v-on:click="turnOnAddMode">Dodaj dział</button>
    <button v-if="!isEditMode && !isAddMode" v-on:click="turnOnEditMode">Edytuj</button>
  </p>
  <p>
    <button v-if="isEditMode" v-on:click="saveChanges">Zapisz</button>
    <button v-if="isEditMode" v-on:click="abandonChanges">Wróć</button>
  </p>
  <router-link v-if="!isAddMode" :to="'/courses'"><button v-on:click="deleteCourse">Usuń</button></router-link>

  <input v-if="isAddMode" v-model="area.name" placeholder="Nazwa działu..."/>
  <p>
  <button v-if="isAddMode" v-on:click="saveAdding">Zapisz</button>
  <button v-if="isAddMode" v-on:click="abandonChanges">Wróć</button>
  </p>
  <ul>
    <li v-if="!isAddMode" v-for ="area in areas" v-bind:key="area.id">
      <router-link :to="'/questions'">{{area.name}}</router-link>
    </li>
  </ul>
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
    },
    deleteCourse () {
      this.isEditMode = false
      axios
        .delete('/api/courses/' + this.courseId)
        .then(() => setTimeout(this.$forceUpdate(), 10000))
    }
  }
}
</script>

<style scoped>

</style>
