<template>
  <div v-if="isVisible">
    <md-tabs md-sync-route class="container" md-alignment="fixed" v-if="teacherView">
      <md-tab id="tab-home" md-label="Główna" to="/"></md-tab>
      <md-tab id="tab-pages" md-label="Pytania" to="/courses"></md-tab>
      <md-tab id="tab-posts" md-label="Testy" to="/tests"></md-tab>
      <md-tab id="tab-settings" md-label="Profil" to="/profile"></md-tab>
    </md-tabs>
    <md-tabs md-sync-route class="container" md-alignment="fixed" v-if="studentView">
      <md-tab id="tab-home" md-label="Główna" to="/"></md-tab>
      <md-tab id="tab-posts" md-label="Testy" to="/tests/mine"></md-tab>
      <md-tab id="tab-settings" md-label="Profil" to="/student/profile"></md-tab>
    </md-tabs>
  </div>
</template>

<script>

export default {
  name: 'Navigation',
  data () {
    return {
      isVisible: true,
      teacherView: false,
      studentView: false,
      links: [
        {
          id: 0,
          text: 'Główna',
          page: '/'
        },
        {
          id: 1,
          text: 'Pytania',
          page: '/courses'
        },
        {
          id: 2,
          text: 'Testy',
          page: '/tests'
        },
        {
          id: 3,
          text: 'Profil',
          page: '/profile'
        }
      ]
    }
  },
  methods: {
    setVisible () {
      let route = this.$route.fullPath
      this.isVisible = route !== '/login' && route !== '/register'
    },
    setView () {
      if (sessionStorage.getItem('user-authority') === 'TEACHER') {
        this.studentView = false
        this.teacherView = true
        this.isVisible = true
      } else {
        if (sessionStorage.getItem('user-authority') === 'STUDENT') {
          this.teacherView = false
          this.studentView = true
          this.isVisible = true
        }
      }
    }
  },
  mounted () {
    this.setVisible()
    this.setView()
  },
  watch: {
    $route (to, from) {
      this.setVisible()
      this.setView()
    }
  }
}

</script>

<style scoped>
.spacing{
  Margin-right: 10px
}
</style>
