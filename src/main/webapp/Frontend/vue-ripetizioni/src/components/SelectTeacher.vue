<template>
<!--  meglio utilizzare le card in questo caso forse-->
  <!--FORM-->
  <div >
    <div class="form-container container align-items-center">
      <div class="title-main">2. Seleziona l'insegnante!</div>
<!--      <select v-model="selectedTeacher">
        <option disabled value=""> Seleziona un insegnante</option>
        <option v-for="subject in subjects" :key="subject.subjectID">{{subject.subjectName}}</option>

      </select>-->
      <div v-for="teacher in teachersBySubject" :key="teacher.teacherID" class="card" style="width: 18rem;">
        <div class="card-body">
          <h5 class="card-title">{{printTeacherName(teacher.teacherID)}}</h5>
          <h6 class="card-subtitle mb-2 text-body-secondary">{{printTeacherEmail(teacher.teacherID)}}</h6>
          <h6 class="card-subtitle mb-2 text-body-secondary">5/5</h6>
          <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
          <RouterLink :to="'/'+this.subjectSelected+'/'+teacher.teacherID"><a href="#" class="card-link">Seleziona </a></RouterLink>

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
      this.subjects = response.data;
      this.teachers=response2.data;
      this.teachersBySubject=response3.data;
      console.log(this.subjects);
      console.log(this.subjectSelected);
      console.log(this.teachersBySubject);
    }catch (e) {
      console.log(e);
    }
  },
  methods: {
    printTeacherName(Id) {
      for (let i = 0; i < this.teachers.length; i++) {
        if (this.teachers[i].teacherId === Id) {
          console.log(this.teachers[i]);
          return this.teachers[i].name+' '+this.teachers[i].surname;
        }
      }
      return null;
    },
    printTeacherEmail(Id) {
      for (let i = 0; i < this.teachers.length; i++) {
        if (this.teachers[i].teacherId === Id) {
          console.log(this.teachers[i]);
          return this.teachers[i].email;
        }
      }
      return null;
    },
  }
}
</script>

<style scoped>

</style>