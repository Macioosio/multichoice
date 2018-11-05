<template>
  <div class="container">
    <div class="md-layout md-gutter">
      <div class="md-layout-item padding-class md-size-20">
        <div class="field">
          <div class="control">
            <label class="label">Kurs</label>
            <div class="select">
              <select v-model="selectedCourseName" v-on:change="fetchSelectedCourseAreas">
                <option v-for="course in courses" :key="course.id">
                  {{course.name}}
                </option>
              </select>
            </div>
          </div>
        </div>
        <div class="field">
          <div class="control">
            <label class="label">Dział</label>
            <div class="select">
              <select v-model="selectedAreaName" :disabled = "!selectedCourseName">
                <option v-for="area in courseAreas" :key="area.id">
                  {{area.name}}
                </option>
              </select>
            </div>
          </div>
        </div>
      </div>
      <div class="md-layout-item padding-class md-size-60">
        <div class="field">
          <label class="label">Treść pytania</label>
          <div class="control">
            <textarea class="textarea" placeholder="Treść" v-model="content"></textarea>
          </div>
        </div>
        <label class="label">Odpowiedzi</label>
        <div class="md-layout md-alignment-center-center" v-for="(answer, index) in answers" :key="index">
          <div class="md-layout-item md-size-5">
            <md-checkbox v-model="answer.correct"></md-checkbox>
          </div>
          <div class="md-layout-item md-size-85">
            <div class="control">
              <input class="input" v-model="answer.content"
                     :class="{ 'is-success': answer.correct }"
                     placeholder="Odpowiedź">
            </div>
          </div>
          <div class="md-layout-item md-size-10">
            <input class="input" type="number" v-model="answer.points"/>
          </div>
        </div>
        <button class="button" @click="addAnswerRow()">Dodaj odpowiedź</button>
        <button class="button" @click="removeAnswerRow()">Usuń odpowiedź</button>
      </div>
      <div class="md-layout-item padding-class md-size-20">
        <button class="button" @click="saveQuestion()">Zapisz</button>
        <router-link class="button" to="/questions/">Wróć</router-link>
        <router-link class="button" v-if="questionId" :to="'/questions/delete/' + questionId">Usuń</router-link>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { mapMutations } from 'vuex'
import { mapMultiRowFields } from 'vuex-map-fields'
import $store from '../store/store'

export default {
  name: 'question_adding',
  props: ['courseId', 'areaId', 'questionId'],
  data () {
    return {
      content: '',
      courses: [],
      selectedCourseName: '',
      courseAreas: [],
      selectedAreaName: ''
    }
  },
  computed: {
    ...mapMultiRowFields(['answers'])
  },
  methods: {
    fetchAllCourses () {
      axios
        .get('/api/courses', {headers: {'Authorization': sessionStorage.getItem('user-token')}})
        .then(response => (this.courses = response.data))
    },
    fetchSelectedCourseAreas () {
      let course = this.courses.find(c => { return c.name === this.selectedCourseName })
      axios
        .get('/api/courses/' + course.id + '/areas', {headers: {'Authorization': sessionStorage.getItem('user-token')}})
        .then(response => (this.courseAreas = response.data))
    },
    ...mapMutations(['addAnswerRow', 'removeAnswerRow']),
    saveQuestion () {
      let id = this.questionId ? this.questionId : null
      let selectedCourse = this.courses.find(c => { return c.name === this.selectedCourseName })
      let selectedArea = this.courseAreas.find(a => { return a.name === this.selectedAreaName })
      let questionAddingForm = {
        'id': id,
        'content': this.content,
        'course': selectedCourse,
        'area': selectedArea,
        'answers': $store.state.answers
      }
      axios
        .post('/api/questions', questionAddingForm, {headers: {'Authorization': sessionStorage.getItem('user-token')}})
        .then(() => (this.clearElements()))
    },
    clearElements () {
      this.content = ''
      this.selectedCourseName = ''
      this.courseAreas = []
      this.selectedAreaName = ''
      $store.state.answers = [
        {
          id: '',
          content: '',
          correct: false,
          points: 0
        },
        {
          id: '',
          content: '',
          correct: false,
          points: 0
        },
        {
          id: '',
          content: '',
          correct: false,
          points: 0
        },
        {
          id: '',
          content: '',
          correct: false,
          points: 0
        }
      ]
    },
    prepareData () {
      this.fetchAllCourses()
      if (this.courseId) {
        this.clearElements()
        axios
          .get('/api/courses/' + this.courseId, {headers: {'Authorization': sessionStorage.getItem('user-token')}})
          .then(response => (this.setDataFromCourse(response.data)))
      }
      if (this.areaId) {
        this.clearElements()
        axios
          .get('/api/areas/' + this.areaId, {headers: {'Authorization': sessionStorage.getItem('user-token')}})
          .then(response => (this.setDataFromArea(response.data)))
      }
      if (this.questionId) {
        axios
          .get('/api/questions/' + this.questionId, {headers: {'Authorization': sessionStorage.getItem('user-token')}})
          .then(response => (this.setDataFromQuestion(response.data)))
      }
    },
    setDataFromCourse (course) {
      this.selectedCourseName = course.name
      this.fetchSelectedCourseAreas()
    },
    setDataFromArea (area) {
      this.setDataFromCourse(area.course)
      this.fetchSelectedCourseAreas()
      this.selectedAreaName = area.name
    },
    setDataFromQuestion (question) {
      this.setDataFromArea(question.area)
      this.content = question.content
      axios
        .get('/api/questions/' + question.id + '/answers', {headers: {'Authorization': sessionStorage.getItem('user-token')}})
        .then(response => (this.setAnswersData(response.data)))
    },
    setAnswersData (answers) {
      let mappedAnswers = answers.map(function (answer) {
        return {
          id: answer.id,
          content: answer.content,
          correct: answer.correct,
          points: answer.points
        }
      })
      $store.state.answers = mappedAnswers
    }
  },
  mounted () {
    this.prepareData()
  }
}
</script>

<style scoped>
  .padding-class {
    padding: 25px;
  }
</style>
