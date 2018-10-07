<template>
  <div class="container">
    <div class="md-layout md-gutter">
      <div class="md-layout-item"></div>
      <div class="md-layout-item">
        <h2 class = "subtitle">Kursy</h2>
        <md-list>
          <md-list-item v-if="!isAddMode"
                        v-for ="course in courses" v-bind:key="course.id"
                        :to="'/course/' + course.id + '/areas'">
            {{course.name}}
          </md-list-item>
        </md-list>
        <input class="input" v-if="isAddMode" v-model="course.name" placeholder="Nazwa kursu..."/>
        <button class="button" v-if="isAddMode" v-on:click="saveChanges">Zapisz</button>
        <button class="button" v-if="isAddMode" v-on:click="abandonChanges">Wróć</button>
      </div>
      <div class="md-layout-item"><button class="button" v-if="!isAddMode" v-on:click="turnOnAddMode">Dodaj</button></div>
    </div>
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
        .then(() => this.loadData())
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
  md-list-item.a{
    text-align: center;
  }
  input::-webkit-input-placeholder {
    color: black;
  }
  /* Firefox < 19 */
  input:-moz-placeholder {
    color: black;
  }

  /* Firefox > 19 */
  input::-moz-placeholder {
    color: black;
  }

  /* Internet Explorer 10 */
  input:-ms-input-placeholder {
    color: black;
  }
</style>
