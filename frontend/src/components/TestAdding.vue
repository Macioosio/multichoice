<template>
  <div class="container padding-class">
    <div class="md-layout md-gutter">
      <div class="md-layout-item">
        <div class="small-padding-class">
          <label class="label">Kurs</label>
          <div class="select">
          <select v-model="course.name">
            <option v-for="course in courses" :key="course.id">
              {{course.name}}
            </option>
          </select>
          </div>
        </div>
        <div class="small-padding-class">
          <label class="label">Start</label>
          <input class="input input-width" required v-model="start" placeholder="Password"/>
        </div>
        <div class="small-padding-class">
          <label class="label">Koniec</label>
          <input class="input input-width" required v-model="end" placeholder="Password"/>
        </div>
        <div class="small-padding-class">
          <label class="label">Hasło</label>
          <input class="input input-width" required v-model="password" type="password" placeholder="Password"/>
        </div>
      </div>
      <div class="md-layout-item">
        <md-table v-model="questions" md-card md-fixed-header>
          <md-table-toolbar>
            <h1 class="md-title md-toolbar-section-start">Pytania</h1>
          </md-table-toolbar>
          <md-table-row slot="md-table-row" slot-scope="{ item }">
            <md-table-cell md-label="Pytanie" md-sort-by="content" width="100">{{ item.content }}</md-table-cell>
            <md-table-cell md-label="Przedmiot" md-sort-by="item.area.course.name">{{ item.area.course.name }}</md-table-cell>
            <md-table-cell md-label="Dział" md-sort-by="item.area">{{ item.area.name }}</md-table-cell>
            <md-table-cell width="50">
              <router-link class="button" :to="'/questions/edit/' + item.id">Pokaż</router-link>
            </md-table-cell>
          </md-table-row>
        </md-table>
      </div>
      <div class="md-layout-item">
        <md-table v-model="students" md-card md-fixed-header>
          <md-table-toolbar>
            <h1 class="md-title md-toolbar-section-start">Studenci</h1>
          </md-table-toolbar>
          <md-table-row slot="md-table-row" slot-scope="{ item }">
            <md-table-cell md-label="email" md-sort-by="content" width="100">{{ item.email }}</md-table-cell>
          </md-table-row>
        </md-table>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import $store from '../store/store'

export default {
  name: 'TestAdding',
  props: ['testId'],
  data () {
    return {
      course: '',
      start: '',
      end: '',
      password: '',
      questions: [],
      students: [],
      courses: []
    }
  },
  mounted () {
    this.fetchData()
  },
  methods: {
    fetchData () {
      this.fetchAllCourses()
      if (this.testId) {
        axios
          .get('/api/tests/' + this.testId, $store.getters.getAuthHeader)
          .then(response => this.setData(response.data))
      }
    },
    setData (data) {
      this.course = data.course
      this.start = data.start
      this.end = data.end
      this.password = data.password
      this.questions = data.questions
      this.students = data.students
    },
    fetchAllCourses () {
      axios
        .get('/api/courses', $store.getters.getAuthHeader)
        .then(response => (this.courses = response.data))
    }
  }
}
</script>

<style scoped>
  .padding-class {
    padding: 25px;
  }
</style>
