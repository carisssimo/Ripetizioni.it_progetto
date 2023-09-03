<template>
  <div class="form-container container rounded py-4 mt-4">
    <div class="container-btn-group">
      <div class="btn-group btn-group-justified" role="group">
        <div class="btn-group" role="group">
          <a class="btn-l btn-login btn btn-success" role="button" v-on:click="backButton">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-chevron-left" viewBox="0 0 16 16">
              <path fill-rule="evenodd" d="M11.354 1.646a.5.5 0 0 1 0 .708L5.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0z"/>
            </svg>
          </a>
        </div>
      </div>
    </div>
    <h3>1. Seleziona una materia: </h3>
    <select class="select-component w-50 mr-4" v-model="selected">
      <option disabled value="">Seleziona una materia</option>
      <option v-for="subject in subjects" :key="subject.subjectID">{{subject.subjectName}}</option>
    </select>
    <RouterLink :to="'/'+this.selected"><a class="btn-l btn-login btn btn-success" role="button">Avanti</a></RouterLink>
  </div>
</template>

<script>
import {subjectsService} from "@/Service/subjectsService";

export default {
  name: "SelectSubject",
  methods: {
    backButton(){
      window.history.back();
    }
  },
  data() {
    return{
      subjects:{},
      selected:this.selected
    }
  },
  created: async function(){
    try {
      let response = await subjectsService.getAllSubjects();
      this.subjects = response;
      console.log(this.subjects);
    }catch (e) {
      console.log(e);
    }
  }
}
</script>

<style scoped>
.select-component{
  margin-top: 1rem;
  height: 2rem;
}

.container-btn-group{
  display: inline-block;
  float: right;
}

</style>