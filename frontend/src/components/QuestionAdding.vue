<template>
  <div class="container">
    <div class="md-layout md-gutter">
      <div class="md-layout-item">
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
      <div class="md-layout-item">
        <div class="field">
          <label class="label">Treść pytania</label>
          <div class="control">
            <textarea class="textarea" placeholder="Treść" v-model="content"></textarea>
          </div>
        </div>
        <label class="label">Odpowiedzi</label>
        <div class="md-layout md-alignment-center-center" v-for="(answer, index) in answers" :key="index">
          <div class="md-layout-item">
            <md-checkbox v-model="answer.isCorrect"></md-checkbox>
          </div>
          <div class="md-layout-item md-size-90">
            <div class="control">
              <input class="input" v-model="answer.content"
                     :class="{ 'is-success': answer.isCorrect }"
                     placeholder="Odpowiedź">
            </div>
          </div>
        </div>
        <button class="button" @click="addAnswerRow()">Dodaj odpowiedź</button>
        <button class="button" @click="removeAnswerRow()">Usuń odpowiedź</button>
      </div>
      <div class="md-layout-item">
        <button class="button" @click="saveQuestion()">Zapisz</button>
        <button class="button" @click="removeAnswerRow()">Wróć</button>
        {{$store.state.answers}}
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
        .get('/api/courses')
        .then(response => (this.courses = response.data))
    },
    fetchSelectedCourseAreas () {
      let course = this.courses.find(c => { return c.name === this.selectedCourseName })
      axios
        .get('/api/courses/' + course.id + '/areas')
        .then(response => (this.courseAreas = response.data))
    },
    ...mapMutations(['addAnswerRow', 'removeAnswerRow']),
    saveQuestion () {
      let selectedCourse = this.courses.find(c => { return c.name === this.selectedCourseName })
      let selectedArea = this.courseAreas.find(a => { return a.name === this.selectedAreaName })
      let questionAddingForm = {
        'id': null,
        'content': this.content,
        'course': selectedCourse,
        'area': selectedArea,
        'answers': $store.state.answers
      }
      axios
        .post('/api/questions', questionAddingForm)
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
          isCorrect: false
        },
        {
          id: '',
          content: '',
          isCorrect: false
        },
        {
          id: '',
          content: '',
          isCorrect: false
        },
        {
          id: '',
          content: '',
          isCorrect: false
        }
      ]
    },
    prepareData () {
      console.log('aaaaa')
      this.fetchAllCourses()
      if (this.courseId) {
        console.log('kurs')
        this.fetchAllCourses()
        let defaultedCourse = this.courses.find(c => { return c.id === this.courseId })
        this.selectedCourseName = defaultedCourse.name
      }
      if (this.areaId) {
        console.log('area')
        axios
          .get('/api/areas/' + this.areaId)
          .then(response => (this.selectedAreaName = response.data.name))
      }
      if (this.questionId) {
        console.log('question')
        axios
          .get('/api/questions/' + this.questionId)
          .then(response => (this.setDataFromQuestion(response.data)))
      }
    },
    setDataFromQuestion (question) {
      this.content = question.content
      this.selectedCourseName = question.course.name
      this.selectedAreaName = question.area.name
      axios
        .get('/api/questions/' + question.id + '/answers')
        .then(response => ($store.state.answers = response.data))
    }
  },
  mounted () {
    this.prepareData()
  }
}
</script>

<style scoped>

</style>
