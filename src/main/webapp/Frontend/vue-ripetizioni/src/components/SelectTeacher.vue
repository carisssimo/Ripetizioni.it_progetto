<template>
<!--  meglio utilizzare le card in questo caso forse-->
  <!--FORM-->
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
    <h3>2. Seleziona l'insegnante: </h3>
    <div class="container d-flex justify-content-center">
      <div v-for="teacher in teachersBySubject" :key="teacher.teacherID" class="card shadow" style="width: 18rem;">
        <div class="card-body">
          <h5 class="card-title">{{printTeacherName(teacher.teacherID)}}</h5>
          <h6 class="card-subtitle mb-2 text-body-secondary">{{printTeacherEmail(teacher.teacherID)}}</h6>
          <h6 class="card-subtitle mb-2 text-body-secondary">5/5</h6>
          <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
          <RouterLink :to="'/'+this.subjectSelected+'/'+teacher.teacherID"><a href="#" class="btn btn-primary" style="background-color: orangered; border: none;">Seleziona </a></RouterLink>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {subjectsService as subjectService, subjectsService} from "@/Service/subjectsService";
import {teacherService} from "@/Service/teachersService";

export default {
  name: "SelectTeacher",
  data() {
    return{
      subjects:{},
      teachers:{},
      teachersBySubject:{},
      subjectSelected:''
    }
  },
  created: async function(){
    try {

      /*al component viene passato il nome della materia dal component precedente*/
      this.subjectSelected= this.$route.params.subjectName;
      let response = await subjectsService.getAllSubjects();
      let response2=await teacherService.getAllTeachers();
      /*vado prima a ricavare l'id del subject'*/
      let subjectSelectedId=await subjectsService.getSubjectId(this.subjectSelected);
      /*solo ora chiamo la servlet chiedendo tutti gli insegnati che insegnano quella materia*/
      console.log(subjectSelectedId);
      let response3=await subjectService.getTeachersBySubject(subjectSelectedId);
      this.subjects = response;
      this.teachers=response2;
      this.teachersBySubject=response3;
      console.log(this.subjects);
      console.log(this.subjectSelected);
      console.log(this.teachersBySubject);
      console.log(this.teachers);
    }catch (e) {
      console.log(e);
    }
  },
  methods: {
    backButton(){
      window.history.back();
    },
    printTeacherName(Id) {
      var self=this;
      var dim =self.teachers.length;
      console.log("questa è la dim"+dim);
      for (let i = 0; i < dim; i++) {
        if (self.teachers[i].teacherId === Id) {
          console.log(self.teachers[i]);
          return self.teachers[i].name+' '+self.teachers[i].surname;
        }
      }
      return null;
    },
    printTeacherEmail(Id) {
      var self=this;
      var dim =self.teachers.length;
      console.log("questa è la dim"+dim);
      for (let i = 0; i < dim; i++) {
        if (self.teachers[i].teacherId === Id) {
          console.log(self.teachers[i]);
          return self.teachers[i].email;
        }
      }
      return null;
    },
  }
}
</script>

<style scoped>
.container-btn-group{
  display: inline-block;
  float: right;
}
</style>