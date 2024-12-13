<template>
  <div class="container">
    <header>
      <IconAthome class="small-icon"/>
      <h1>APLICACIÓN ATHOME</h1>
      <IconAthome class="small-icon"/>
    </header>
    <main>
      <p>¡Athome te ayuda a gestionar tu casa compartida de forma fácil! Descarga ahora la aplicación para mejorar la convivencia.</p>
      <router-link to="/descargar">
        <button>Descargar APK</button>
      </router-link>
    </main>
    <article>
      <img src="../assets/logo.png" alt="Logo" class="small-icon" />
      <ul class="funciones">
        <li> Informes de estado de cada usuario del hogar</li>
        <li> Organizacion de Inmuebles y sus productos</li>
        <li> Reserva de espacios por calendario y eventos</li>
      </ul>
      <img src="../assets/logo.png" alt="Logo" class="small-icon" />
    </article>
    <footer>
      <p>&copy; 2024 Athome. Todos los derechos reservados.</p>
    </footer>
  </div>
</template>

<script>
import IconAthome from "@/components/icons/IconAthome.vue";

export default {
  name: "PaginaDescargar",
  components: { IconAthome },
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Audiowide&display=swap');

.small-icon {
  width: 75px;
  height: 75px;
  margin: 0 10px;
  animation: bounce 2s infinite;
}

.container {
  text-align: center;
  margin-top: 30px;
  padding: 20px;
  background: linear-gradient(to bottom, #42b983, #a2d5ab);
  color: white;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

button {
  background-color: #42b983;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-family: 'Audiowide', sans-serif;
}

button:hover {
  background-color: #358a6b;
}

.funciones {
  list-style: none;
  padding: 0;
  margin: 20px 0;
}

.funciones li {
  font-size: 1em;
  margin: 10px 0;
  color: white;
}

header {
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: 'Audiowide', cursive;
  font-size: 1.3em;
  color: black;
}

article {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

footer {
  text-align: center;
  margin-top: 20px;
  font-size: 0.8em;
  font-family: Arial, sans-serif;
  color: #666;
}
</style>