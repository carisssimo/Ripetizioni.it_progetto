<template>

  <nav class="navbar navbar-light bg-light">
    <h1 class="title-ripetizioni">Ripetizioni.it</h1>
    <form class="form-inline">

      <RouterLink to="/login"><a class="btn-login btn btn-primary" role="button">Login</a></RouterLink>

    </form>
  </nav>
  <!--</div>-->

  <!--MAIN-->
  <div class="container">
    <div class="main-row row">
      <!--colonna di titolo e descrizione-->
      <div class="col">
        <div class="title-container container">
          <h1 class="title-main">Scegli la materia e il tuo Prof</h1>
          <p class="p-start">ripetizioni.it ti aiuta a prenotare ripetizioni con i migliori docenti della tua
            città selezionando le date e gli orari che ti fanno più comodo. Registrati per prenotare la tua
            lezione </p>
        </div>
      </div>
      <!--colonna di carosello e descrizione(sarebbe utile eliminarlo arrivati ad una certa soglia )-->
      <div class="carousel-col col ">
        <div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
          <div class="carousel-inner">
            <div class="image-item carousel-item active">
              <img src="../images/photo-1544456203-0af5a69f5789.webp" class="d-block w-100" alt="...">
            </div>
            <div class="image-item carousel-item">
              <img src="../images/photo-1524508762098-fd966ffb6ef9.webp" class="d-block w-100" alt="...">
            </div>
            <div class="image-item carousel-item">
              <img src="../images/premium_photo-1663013747131-1931a76aa498.webp" class="d-block w-100" alt="...">
            </div>
          </div>
          <button class="carousel-control-prev" type="button" data-target="#carouselExampleControls"
                  data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
          </button>
          <button class="carousel-control-next" type="button" data-target="#carouselExampleControls"
                  data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
          </button>
        </div>

      </div>
    </div>
    <!-- riga per le descrizioni varie -->
    <div class="card-row row align-items-center">
      <div class="event-col col">
        <h1>Eventi</h1>
        <p>scopri tutti gli eventi per incontrare altri studenti</p>
      </div>
      <div class="event-col col">
        <h1>Locali</h1>
        <p>scopri tutti gli eventi della nostro borgo</p>
      </div>
      <div class="event-col col">
        <h1>Partner</h1>
        <p>scopri tutti gli eventi della nostro borgo</p>
      </div>
    </div>
    <div class="container">
      <div class="row">
        <div class="image-col col">

        </div>
        <div class="col">
          <div class="title-container container">
            <h1 class="title-main">Ora disponibili anche online</h1>
            <p class="p-start">I migliori docenti della città direttamente sul tuo schermo. Prenota subito una lezione e seguila
              comodamente da casa</p>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col">
          <p>
            <button class="btn-login btn btn-primary" type="button" data-toggle="collapse" data-target="#collapseCourses" aria-expanded="false" aria-controls="collapseWidthExample">
              Materie disponibili
            </button>
          </p>
          <div style="min-height: 120px;">
            <div class="collapse width" id="collapseCourses">
              <div class="card card-body" style="width: 320px;">
                <ul class="list-group">
                  <li v-for="subject in subjects" :key="subject.subjectID" class="list-group-item">{{subject.subjectName}}</li>

<!--                  <li class="list-group-item">A second item</li>
                  <li class="list-group-item">A third item</li>
                  <li class="list-group-item">A fourth item</li>
                  <li class="list-group-item">And a fifth one</li>-->
                </ul>
              </div>
            </div>
          </div>
        </div>
        <div class="col">
          <p>
            <button class="btn-login btn btn-primary" type="button" data-toggle="collapse" data-target="#collapseSlots" aria-expanded="false" aria-controls="collapseWidthExample">
              Slot disponibili
            </button>
          </p>
          <div style="min-height: 120px;">
            <div class="collapse width" id="collapseSlots">
              <div class="card card-body" style="width: 320px;">
                <ul class="list-group">
                  <li class="list-group-item">Vai alle prenotazioni disponibili</li>
                </ul>
              </div>
            </div>
          </div>
        </div>
        <div class="col">
          <p>
            <button class="btn-login btn btn-primary" type="button" data-toggle="collapse" data-target="#collapseProfessors" aria-expanded="false" aria-controls="collapseWidthExample">
              Insegnanti disponibili
            </button>
          </p>
          <div style="min-height: 120px;">
            <div class="collapse width" id="collapseProfessors">
              <div class="list-group">
                <a v-for="teacher in teachers" :key="teacher.name" class="list-group-item list-group-item-action">{{teacher.name}} {{teacher.surname}}</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

  </div>






</template>

<script>
import {teacherService} from "@/Service/teachersService";
import {subjectsService} from "@/Service/subjectsService";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Home",
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
    }catch(e){
      console.log(e);
    }


  }
}
</script>

<style>
.image-col{
  background-image: url("../images/photo-1500989145603-8e7ef71d639e.webp");
  margin-top: 54px;
  margin-bottom: 54px;
  border-radius: 20px;
  overflow: hidden;
}
</style>