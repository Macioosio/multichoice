<template>
  <div class="container padding-class">
    <div class="md-layout md-gutter" v-if="!isQuestionChoosingMode && !isStudentChoosingMode">
      <div class="md-layout-item md-size-20">
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
        <datetime input-class="input" type="datetime" v-model="start"></datetime>
        <label class="label">Koniec</label>
        <datetime input-class="input" type="datetime" v-model="end"></datetime>
        </div>
        <div class="small-padding-class">
          <label class="label">Hasło</label>
          <input class="input input-width" required v-model="password" placeholder="Password"/>
        </div>
        <div class="small-padding-class">
          <button class="button" v-on:click="saveAndClose">Zapisz</button>
          <router-link class="button" to="/tests">Wróć</router-link>
        </div>
      </div>
      <div class="md-layout-item md-size-50">
        <md-table v-model="questions" md-card md-fixed-header>
          <md-table-toolbar>
            <h1 class="md-title md-toolbar-section-start">Pytania</h1>
            <button class="button" v-on:click="setQuestionChoosingMode">Dodaj</button>
          </md-table-toolbar>
          <md-table-row slot="md-table-row" slot-scope="{ item }">
            <md-table-cell md-label="Pytanie" md-sort-by="content" width="100">{{ item.content }}</md-table-cell>
            <md-table-cell md-label="Przedmiot" md-sort-by="item.area.course.name">{{ item.area.course.name }}</md-table-cell>
            <md-table-cell md-label="Dział" md-sort-by="item.area">{{ item.area.name }}</md-table-cell>
            <md-table-cell width="50">
              <router-link class="button is-small" :to="'/questions/edit/' + item.id">Pokaż</router-link>
              <button class="button is-small" v-on:click="removeQuestion(item)">Usuń</button>
            </md-table-cell>
          </md-table-row>
        </md-table>
      </div>
      <div class="md-layout-item md-size-30">
        <md-table v-model="students" md-card md-fixed-header>
          <md-table-toolbar>
            <h1 class="md-title md-toolbar-section-start">Studenci</h1>
            <button class="button" v-on:click="setStudentChoosingMode">Dodaj</button>
          </md-table-toolbar>
          <md-table-row slot="md-table-row" slot-scope="{ item }">
            <md-table-cell md-label="email" md-sort-by="content">{{ item.email }}</md-table-cell>
            <md-table-cell width="50"><button class="button is-small" v-on:click="removeStudent(item)">Usuń</button></md-table-cell>
          </md-table-row>
        </md-table>
      </div>
    </div>
    <div class="container padding-class" v-if="isQuestionChoosingMode">
      <md-table v-model="allQuestions" md-card md-fixed-header @md-selected="onSelectQuestions">
        <md-table-toolbar>
          <h1 class="md-title md-toolbar-section-start">Pytania</h1>
          <button class="button" v-on:click="isQuestionChoosingMode=false">Zapisz</button>
          <button class="button" v-on:click="isQuestionChoosingMode=false">Wróć</button>
        </md-table-toolbar>
        <md-table-row slot="md-table-row" slot-scope="{ item }" md-selectable="multiple">
          <md-table-cell md-label="Pytanie" md-sort-by="content" width="800">{{ item.content }}</md-table-cell>
          <md-table-cell md-label="Przedmiot" md-sort-by="item.area.course.name">{{ item.area.course.name }}</md-table-cell>
          <md-table-cell md-label="Dział" md-sort-by="item.area">{{ item.area.name }}</md-table-cell>
        </md-table-row>
      </md-table>
    </div>
    <div class="container padding-class" v-if="isStudentChoosingMode">
      <md-table v-model="allStudents" md-card md-fixed-header @md-selected="onSelectStudents">
        <md-table-toolbar>
          <h1 class="md-title md-toolbar-section-start">Studenci</h1>
          <button class="button" v-on:click="isStudentChoosingMode=false">Zapisz</button>
          <button class="button" v-on:click="isStudentChoosingMode=false">Wróć</button>
        </md-table-toolbar>
        <md-table-row slot="md-table-row" slot-scope="{ item }" md-selectable="multiple">
          <md-table-cell md-label="email" md-sort-by="content">{{ item.email }}</md-table-cell>
        </md-table-row>
      </md-table>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import $store from '../store/store'
import {router} from '../router/index'
import * as moment from 'moment'

export default {
  name: 'TestAdding',
  props: ['testId'],
  data () {
    return {
      course: {},
      start: {},
      end: {},
      password: '',
      questions: [],
      students: [],
      courses: [],
      allQuestions: [],
      allStudents: [],
      selectedQuestions: [],
      selectedStudents: [],
      isQuestionChoosingMode: false,
      isStudentChoosingMode: false
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
      this.start = this.convertDate(data.start)
      this.end = this.convertDate(data.end)
      this.password = data.password
      this.questions = data.questions
      this.students = data.students
    },
    fetchAllCourses () {
      axios
        .get('/api/courses', $store.getters.getAuthHeader)
        .then(response => (this.courses = response.data))
    },
    convertDate (dateJavaFormat) {
      // 2018-10-19T10:00:00.628+0000
      var date = moment(dateJavaFormat).toISOString()
      return date
    },
    removeQuestion (question) {
      this.questions = this.questions.filter(function (q) {
        return q.id !== question.id
      })
    },
    removeStudent (student) {
      this.students = this.students.filter(function (s) {
        return s.id !== student.id
      })
    },
    saveAndClose () {
      if (this.testId) {
        let course = {
          'id': this.testId,
          'start': this.start,
          'end': this.end,
          'password': this.password,
          'course': this.getCourse(this.course.name),
          'questions': this.questions,
          'students': this.students
        }
        axios
          .put('/api/tests', course, $store.getters.getAuthHeader)
          .then(() => router.push('/tests'))
      }
    },
    getCourse (courseName) {
      let course = this.courses.find(function (c) {
        return c.name === courseName
      })
      return course
    },
    setQuestionChoosingMode () {
      this.isQuestionChoosingMode = true
      axios
        .get('/api/questions', $store.getters.getAuthHeader)
        .then(response => (this.allQuestions = response.data))
    },
    setStudentChoosingMode () {
      this.isStudentChoosingMode = true
      axios
        .get('/api/students', $store.getters.getAuthHeader)
        .then(response => (this.allStudents = response.data))
    },
    onSelectQuestions (questions) {
      this.selectedQuestions = questions
    },
    onSelectStudents (students) {
      this.selectedStudents = students
    }
  }
}
</script>

<style scoped>
  .padding-class {
    padding: 25px;
  }
  .small-padding-class {
    padding: 10px;
  }
</style>
