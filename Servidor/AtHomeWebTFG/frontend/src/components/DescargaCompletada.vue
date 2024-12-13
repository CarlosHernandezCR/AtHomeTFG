<template>
  <div class="descarga-completada">
    <h1>Descarga en progreso</h1>
    <p>Estamos preparando tu descarga, por favor espera unos momentos...</p>
    <div class="spinner"></div>
    <p v-if="apkDownloadUrl">
      Si la descarga no comienza automáticamente, haz clic en el siguiente enlace:
      <a :href="apkDownloadUrl" download>Descargar APK</a>
    </p>
  </div>
</template>

<style scoped>
.descarga-completada {
  text-align: center;
  margin-top: 50px;
  font-family: Arial, sans-serif;
}
.spinner {
  margin: 20px auto;
  width: 50px;
  height: 50px;
  border: 5px solid #f3f3f3;
  border-top: 5px solid #42b983;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}
@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}
a {
  color: #42b983;
  text-decoration: none;
  font-weight: bold;
}
a:hover {
  text-decoration: underline;
}
</style>

<script>
export default {
  name: "DescargaCompletada",
  data() {
    return {
      apkDownloadUrl: "/descargar/apk",
    };
  },
  mounted() {
    setTimeout(() => {
      window.location.href = this.apkDownloadUrl;
    }, 3000);
  },
};
</script>