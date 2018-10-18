/* eslint-disable no-unused-vars */
import Vue from 'vue'
import Vuex from 'vuex'
import { getField, updateField } from 'vuex-map-fields'
import axios from 'axios'

Vue.use(Vuex)

export default new Vuex.Store({
  // ...
  state: {
    authToken: localStorage.getItem('user-token') || '',
    authority: localStorage.getItem('user-authority') || '',
    answers: [
      {
        id: '',
        content: '',
        correct: false
      },
      {
        id: '',
        content: '',
        correct: false
      },
      {
        id: '',
        content: '',
        correct: false
      },
      {
        id: '',
        content: '',
        correct: false
      }
    ]
  },
  getters: {
    getField,
    getAuthHeader: (state) => {
      return {
        headers: {'Authorization': state.authToken}
      }
    }
  },
  mutations: {
    updateField,
    addAnswerRow (state) {
      state.answers.push({
        id: '',
        correct: false,
        content: ''
      })
    },
    removeAnswerRow (state) {
      let answerToRemove = state.answers.pop()
      if (answerToRemove) {
        axios
          .delete('/api/answers/' + answerToRemove.id, this.getters.getAuthHeader)
      }
    }
  }
})
