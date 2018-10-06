<template>
  <div>
    <h2>Kursy</h2>
    <button v-if="!isAddMode" v-on:click="turnOnAddMode">Dodaj</button>
      <input v-if="isAddMode" v-model="course.name" placeholder="Nazwa kursu..."/>
      <p>
        <button v-if="isAddMode" v-on:click="saveChanges">Zapisz</button>
        <button v-if="isAddMode" v-on:click="abandonChanges">Wróć</button>
      </p>

    <ul>
    <li v-if="!isAddMode" v-for ="course in courses" v-bind:key="course.id">
      <router-link :to="'/course/' + course.id + '/areas'">{{course.name}}</router-link>
    </li>
  </ul>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'Courses',
  data () {
    return {
      courses: [],
      isAddMode: false,
      course: {
        'name': null
      }
    }
  },
  mounted () {
    this.loadData()
  },
  methods: {
    loadData () {
      axios
        .get('/api/courses')
        .then(response => (this.courses = response.data))
    },
    saveChanges () {
      axios
        .post('/api/courses/', this.course)
      this.courses.push(this.course)
      this.isAddMode = false
    },
    abandonChanges () {
      this.isAddMode = false
    },
    turnOnAddMode () {
      this.isAddMode = true
    }
  }
}
</script>

<style scoped>

</style>
