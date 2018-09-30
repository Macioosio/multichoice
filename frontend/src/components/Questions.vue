<template>
  <div>
    <p>Pytania</p>
    <button class=”Search__button” @click="callRestService()">CALL Spring Boot REST backend service</button>

    <h3>{{ response }}</h3>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'Questions',
  methods: {
    callRestService
  },

  data () {
    return {
      response: [],
      errors: []
    }
  }
}
function callRestService () {
  axios.get(`api/courses`)
    .then(response => {
      // JSON responses are automatically parsed.
      this.response = response.data
    })
    .catch(e => {
      this.errors.push(e)
    })
}
</script>

<style scoped>

</style>
