<template>
<div>
  <p>
  <p>Pytania</p>
  <ul>
    <li v-for ="area in areas" v-bind:key="area.id">
      {{area.name}}
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
      areas: null
    }
  },
  mounted () {
    this.fetchCourseAreas()
  },
  methods: {
    fetchCourseAreas () {
      axios
        .get('/api/courses/' + this.courseId + '/areas')
        .then(response => (this.areas = response.data))
    }
  }
}
</script>

<style scoped>

</style>
