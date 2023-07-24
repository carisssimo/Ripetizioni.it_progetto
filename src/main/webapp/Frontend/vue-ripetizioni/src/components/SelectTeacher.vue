<template>
<!--  meglio utilizzare le card in questo caso forse-->
  <!--FORM-->
  <div >
    <div class="form-container container align-items-center">
      <div class="title-main">2. Seleziona l'insegnante!</div>
      <select v-model="selectedTeacher">
        <option disabled value=""> Seleziona un insegnante</option>
        <option v-for="subject in subjects" :key="subject.subjectID">{{subject.subjectName}}</option>

      </select>
    </div>
  </div>
</template>

<script>
import {subjectsService} from "@/Service/subjectsService";
import {teacherService} from "@/Service/teachersService";

export default {
  name: "SelectTeacher",
  data() {
    return{
      selectedTeacher:this.selectedTeacher,
      subjects:{},
      teachers:{},
      teachersBySubject:{}
    }
  },
  created: async function(){
    try {

      let subjectSelected= this.$route.params.subjectName
      let response = await subjectsService.getAllSubjects();
      let response2=await teacherService.getAllTeachers();
      let subjectSelectedId=await subjectsService.getSubjectId(subjectSelected);
      let response3=await teacherService.getTeachersBySubject(subjectSelectedId);
      this.subjects = response.data;
      this.teachers=response2.data;
      this.teachersBySubject=response3.data();
      console.log(this.subjects);
      console.log(subjectSelected);
      console.log(this.teachersBySubject);
    }catch (e) {
      console.log(e);
    }
  }
}
</script>

<style scoped>

</style>