<template>
  <div class="container padding-class">
    <form class="register" @submit.prevent="register">
      <h2 class = "subtitle">Profil</h2>
      <div class="small-padding-class">
        <label class="label">Email</label>
        <input class="input input-width" required v-model="email" type="text" placeholder="email@email.com"/>
      </div>
      <div class="small-padding-class">
        <label class="label">Hasło</label>
        <input class="input input-width" required v-model="password" type="password" placeholder="Password"/>
      </div>
      <div class="small-padding-class">
        <label class="label">Powtórz hasło</label>
        <input class="input input-width" required v-model="passwordRepeat" type="password" placeholder="Password"/>
      </div>
      <button class="button is-warning" type="submit">Zapisz</button>
      <md-snackbar :md-position="snackBarPosition" :md-duration="snackBarDuration" :md-active.sync="showSnackbar" md-persistent>
        <span>{{snackBarText}}</span>
      </md-snackbar>
    </form>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'Profile',
  data () {
    return {
      email: '',
      password: '',
      passwordRepeat: '',
      showSnackbar: false,
      snackBarText: '',
      snackBarDuration: 10000,
      snackBarPosition: 'center'
    }
  },
  methods: {
    register () {
      if (this.password !== this.passwordRepeat) {
        this.showSnackbar = true
        this.snackBarText = 'Podane hasła nie są jednakowe'
      } else {
        if (this.email !== sessionStorage.getItem('user-email')) {
          this.showSnackbar = true
          this.snackBarText = 'Niepoprawny adres e-mail'
        } else {
          let userData = {'email': this.email, 'password': this.password}
          if (sessionStorage.getItem('user-authority') === 'STUDENT') {
            this.modifyStudent(userData)
          }
          if (sessionStorage.getItem('user-authority') === 'TEACHER') {
            this.modifyTeacher(userData)
          }
        }
      }
    },
    modifyStudent (studentData) {
      axios
        .put('/api/students', studentData, {headers: {'Authorization': sessionStorage.getItem('user-token')}})
        .then(() => this.showPositiveSnackbar())
    },
    modifyTeacher (teacherData) {
      axios
        .put('/api/teachers', teacherData, {headers: {'Authorization': sessionStorage.getItem('user-token')}})
        .then(() => this.showPositiveSnackbar())
    },
    showPositiveSnackbar () {
      this.showSnackbar = true
      this.snackBarText = 'Zapisano!'
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
  .input-width {
    width: 30%;
  }
</style>
