import Vue from 'vue';
import Router from 'vue-router';
import PaginaDescargar from '@/components/PaginaDescargar.vue';
import DescargaCompletada from '@/components/DescargaCompletada.vue';

Vue.use(Router);

export default new Router({
    mode: 'history',
    routes: [
        { path: '/', component: PaginaDescargar },
        { path: '/descargar', component: DescargaCompletada }
    ]
});