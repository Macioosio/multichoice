/* eslint-disable no-unused-vars */
import Vue from 'vue'
import Vuex from 'vuex'
import { getField, updateField } from 'vuex-map-fields'
import axios from 'axios'

Vue.use(Vuex)

export default new Vuex.Store({
  // ...
  state: {
    answers: [
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
  getters: {
    getField
  },
  mutations: {
    updateField,
    addAnswerRow (state) {
      state.answers.push({
        id: '',
        isCorrect: false,
        content: ''
      })
    },
    removeAnswerRow (state) {
      let answerToRemove = state.answers.pop()
      if (!answerToRemove) {
        axios
          .delete('/api/answers/' + answerToRemove.id)
      }
    }
  }
})
