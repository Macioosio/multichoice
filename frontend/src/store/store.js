/* eslint-disable no-unused-vars */
import Vue from 'vue'
import Vuex from 'vuex'
import { getField, updateField } from 'vuex-map-fields'

Vue.use(Vuex)

export default new Vuex.Store({
  // ...
  state: {
    answers: [
      {
        content: '',
        isCorrect: false
      },
      {
        content: '',
        isCorrect: false
      },
      {
        content: '',
        isCorrect: false
      },
      {
        content: '',
        isCorrect: false
      }
    ]
  },
  getters: {
    getField
  },
  mutations: {
    updateField,
    addAnswerRow (state) {
      state.answers.push({
        isCorrect: false,
        content: ''
      })
    },
    removeAnswerRow (state) {
      state.answers.pop()
    }
  }
})
