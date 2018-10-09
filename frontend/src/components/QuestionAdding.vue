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
            <textarea class="textarea" placeholder="Treść"></textarea>
          </div>
        </div>
        <label class="label">Odpowiedzi</label>
            <div v-for="(answer, index) in answers" :key="index">
              <component :is="answer"></component>
            </div>
        <button class="button" @click="addAnswer()">Dodaj odpowiedź</button>
        <button class="button" @click="removeAnswer()">Usuń odpowiedź</button>
      </div>
      <div class="md-layout-item">
        <button class="button" @click="addAnswer()">Zapisz</button>
        <button class="button" @click="removeAnswer()">Wróć</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import Vue from 'vue'

Vue.component('answer', {
  template: '<div class="md-layout md-alignment-center-center">\n' +
    '          <div></div>\n' +
    '          <div class="md-layout-item"><md-checkbox v-model="isCorrect"></md-checkbox></div>\n' +
    '          <div class="md-layout-item md-size-90">\n' +
    '            <div class="control">\n' +
    '              <input class="input" v-bind:class="{ \'is-success\': isCorrect }" placeholder="Odpowiedź">\n' +
    '            </div>\n' +
    '          </div>\n' +
    '          <div v-for="(answer, index) in answers" :key="index">\n' +
    '            <component :is="answer"></component>\n' +
    '          </div>\n' +
    '        </div>',
  data () {
    return {
      isCorrect: false
    }
  }
})

export default {
  name: 'QuestionAdding',
  data () {
    return {
      courses: [],
      selectedCourseName: '',
      courseAreas: [],
      selectedAreaName: '',
      answers: ['answer', 'answer', 'answer', 'answer']
    }
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
    addAnswer () {
      this.answers.push('answer')
    },
    removeAnswer () {
      this.answers.pop()
    }
  },
  mounted () {
    this.fetchAllCourses()
  }
}
</script>

<style scoped>

</style>
