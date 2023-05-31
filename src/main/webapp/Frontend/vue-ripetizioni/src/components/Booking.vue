<template>
  <!--NAVBAR PRINCIPALE-->
  <!--<div class="container-fluid">-->
  <nav class="navbar navbar-light bg-light">
    <h1 class="title-ripetizioni">Ripetizioni.it</h1>
    <form class="form-inline">

      <a class="btn-login btn btn-primary"  role="button">Registrati</a>
    </form>
  </nav>
  <!--</div>-->

  <!--FORM per selezionare orario e materia -->
  <div class="form-container-selector container">
    <form class="form-inline">
      <label class="my-1 mr-2" for="inlineFormCustomSelectCourse">Materia</label>
      <select class="custom-select my-1 mr-sm-2" id="inlineFormCustomSelectCourse">
        <option selected>Scegli la materia </option>
        <option v-for="subject in subjects" :key="subject.subjectID" value="1">{{subject.subjectName}}</option>
      </select>

      <label class="my-1 mr-2" for="inlineFormCustomSelectTime">Professore</label>
      <select class="custom-select my-1 mr-sm-2" id="inlineFormCustomSelectTime">
        <option selected>Scegli professore</option>
        <option v-for="teacher in teachers" :key="teacher.name" value="1">{{teacher.name}} {{teacher.surname}}</option>
      </select>



      <button type="submit" class="btn-login btn btn-primary my-1">Submit</button>
    </form>
  </div>
</template>

<script>
import {teacherService} from "@/Service/teachersService";
import {subjectsService} from "@/Service/subjectsService";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Booking",
  data: function (){
    return{
      loading:false,
      teachers:{},
      subjects:{}
    }
  },
  created: async function(){
    try{
      this.loading=true;
      let response=await teacherService.getAllTeachers();
      let response2=await subjectsService.getAllSubjects();
      this.loading=false;
      this.teachers=response.data;
      this.subjects=response2.data;
      console.log(this.subjects);
      console.log(this.teachers);
    }catch(e){
      console.log(e);
    }


  }
}
</script>

<style scoped>

</style>