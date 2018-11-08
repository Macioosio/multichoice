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
            <label class="label">Metoda oceniania</label>
            <div class="select">
              <select v-model="gradingMethod">
                <option v-for="(gMethod, index) in gradingMethods" :key="index">
                  {{gMethod}}
                </option>
              </select>
            </div>
        </div>
        <label class="label">Nawigowalność</label>
          <div class="control">
          <label class="radio">
            <input type="radio" name="navigable" value="true" v-model="navigable">
            Tak
          </label>
          <label class="radio">
            <input type="radio" name="navigable" value="false" v-model="navigable">
            Nie
          </label>
        </div>
        <div class="small-padding-class">
          <label class="label">Punkty</label>
          <input class="input" disabled v-model="points"/>
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
          <button class="button" v-on:click="addSelectedQuestions">Dodaj</button>
          <button class="button" v-on:click="clearSelectedQuestions">Wróć</button>
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
          <button class="button" v-on:click="addSelectedStudents">Dodaj</button>
          <button class="button" v-on:click="clearSelectedStudents">Wróć</button>
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
      gradingMethod: '',
      gradingMethods: ['DO', 'CO', 'TO', 'TO_MINUS'],
      navigable: true,
      points: 0,
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
          .get('/api/tests/' + this.testId, {headers: {'Authorization': sessionStorage.getItem('user-token')}})
          .then(response => this.setData(response.data))
      }
    },
    setData (data) {
      this.course = data.course
      this.start = this.convertDate(data.start)
      this.end = this.convertDate(data.end)
      this.password = data.password
      this.gradingMethod = data.gradingMethod
      this.points = data.points
      this.navigable = data.navigable
      this.questions = data.questions
      this.students = data.students
    },
    fetchAllCourses () {
      axios
        .get('/api/courses', {headers: {'Authorization': sessionStorage.getItem('user-token')}})
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
      this.points = this.calculatePoints()
    },
    removeStudent (student) {
      this.students = this.students.filter(function (s) {
        return s.id !== student.id
      })
    },
    saveAndClose () {
      if (this.testId) {
        let test = {
          'id': this.testId,
          'start': this.start,
          'end': this.end,
          'password': this.password,
          'gradingMethod': this.gradingMethod,
          'navigable': this.navigable,
          'course': this.getCourse(this.course.name),
          'questions': this.questions,
          'students': this.students
        }
        axios
          .put('/api/tests', test, {headers: {'Authorization': sessionStorage.getItem('user-token')}})
          .then(() => router.push('/tests'))
      } else {
        let test = {
          'start': this.start,
          'end': this.end,
          'password': this.password,
          'gradingMethod': this.gradingMethod,
          'navigable': this.navigable,
          'course': this.getCourse(this.course.name),
          'questions': this.questions,
          'students': this.students
        }
        axios
          .post('/api/tests', test, {headers: {'Authorization': sessionStorage.getItem('user-token')}})
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
        .get('/api/questions', {headers: {'Authorization': sessionStorage.getItem('user-token')}})
        .then(response => (this.allQuestions = this.filterAlreadySelectedQuestions(response.data)))
    },
    setStudentChoosingMode () {
      this.isStudentChoosingMode = true
      axios
        .get('/api/students', {headers: {'Authorization': sessionStorage.getItem('user-token')}})
        .then(response => (this.allStudents = this.filterAlreadySelectedStudents(response.data)))
    },
    onSelectQuestions (questions) {
      this.selectedQuestions = questions
    },
    onSelectStudents (students) {
      this.selectedStudents = students
    },
    addSelectedQuestions () {
      this.questions = this.questions.concat(this.selectedQuestions)
      this.points = this.calculatePoints()
      this.clearSelectedQuestions()
    },
    calculatePoints () {
      let maxPoints = this.questions
        .filter(question => question.points > 0)
        .map(question => question.points)
        .reduce((a, b) => a + b, 0)
      console.log(maxPoints)
      return maxPoints
    },
    addSelectedStudents () {
      this.students = this.students.concat(this.selectedStudents)
      this.clearSelectedStudents()
    },
    clearSelectedQuestions () {
      this.selectedQuestions = []
      this.isQuestionChoosingMode = false
    },
    clearSelectedStudents () {
      this.selectedStudents = []
      this.isStudentChoosingMode = false
    },
    filterAlreadySelectedQuestions (fetchedQuestions) {
      for (let question of this.questions) {
        fetchedQuestions = fetchedQuestions.filter(function (s) {
          return s.id !== question.id
        })
      }
      return fetchedQuestions
    },
    filterAlreadySelectedStudents (fetchedStudents) {
      for (let student of this.students) {
        fetchedStudents = fetchedStudents.filter(function (s) {
          return s.id !== student.id
        })
      }
      return fetchedStudents
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
