<template>
  <div>
    <p>Pytania</p>
    <ul>
    <li v-for ="course in courses" v-bind:key="course.id">
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
      courses: null
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
    }
  }
}
</script>

<style scoped>

</style>
