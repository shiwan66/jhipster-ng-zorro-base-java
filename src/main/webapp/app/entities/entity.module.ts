import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'animal-tipo-de-alteracao',
        loadChildren: () =>
          import('./animal-tipo-de-alteracao/animal-tipo-de-alteracao.module').then(m => m.NgzorroAnimalTipoDeAlteracaoModule)
      },
      {
        path: 'animal',
        loadChildren: () => import('./animal/animal.module').then(m => m.NgzorroAnimalModule)
      },
      {
        path: 'animal-tipo-de-vacina',
        loadChildren: () => import('./animal-tipo-de-vacina/animal-tipo-de-vacina.module').then(m => m.NgzorroAnimalTipoDeVacinaModule)
      },
      {
        path: 'raca',
        loadChildren: () => import('./raca/raca.module').then(m => m.NgzorroRacaModule)
      },
      {
        path: 'tutor',
        loadChildren: () => import('./tutor/tutor.module').then(m => m.NgzorroTutorModule)
      },
      {
        path: 'endereco',
        loadChildren: () => import('./endereco/endereco.module').then(m => m.NgzorroEnderecoModule)
      },
      {
        path: 'animal-observacao',
        loadChildren: () => import('./animal-observacao/animal-observacao.module').then(m => m.NgzorroAnimalObservacaoModule)
      },
      {
        path: 'animal-alteracao',
        loadChildren: () => import('./animal-alteracao/animal-alteracao.module').then(m => m.NgzorroAnimalAlteracaoModule)
      },
      {
        path: 'animal-veterinario',
        loadChildren: () => import('./animal-veterinario/animal-veterinario.module').then(m => m.NgzorroAnimalVeterinarioModule)
      },
      {
        path: 'animal-vacina',
        loadChildren: () => import('./animal-vacina/animal-vacina.module').then(m => m.NgzorroAnimalVacinaModule)
      },
      {
        path: 'animal-vermifugo',
        loadChildren: () => import('./animal-vermifugo/animal-vermifugo.module').then(m => m.NgzorroAnimalVermifugoModule)
      },
      {
        path: 'animal-carrapaticida',
        loadChildren: () => import('./animal-carrapaticida/animal-carrapaticida.module').then(m => m.NgzorroAnimalCarrapaticidaModule)
      },
      {
        path: 'animal-cio',
        loadChildren: () => import('./animal-cio/animal-cio.module').then(m => m.NgzorroAnimalCioModule)
      },
      {
        path: 'consumo',
        loadChildren: () => import('./consumo/consumo.module').then(m => m.NgzorroConsumoModule)
      },
      {
        path: 'atendimento',
        loadChildren: () => import('./atendimento/atendimento.module').then(m => m.NgzorroAtendimentoModule)
      },
      {
        path: 'venda',
        loadChildren: () => import('./venda/venda.module').then(m => m.NgzorroVendaModule)
      },
      {
        path: 'venda-consumo',
        loadChildren: () => import('./venda-consumo/venda-consumo.module').then(m => m.NgzorroVendaConsumoModule)
      },
      {
        path: 'movimentacao-de-estoque',
        loadChildren: () =>
          import('./movimentacao-de-estoque/movimentacao-de-estoque.module').then(m => m.NgzorroMovimentacaoDeEstoqueModule)
      },
      {
        path: 'anexo',
        loadChildren: () => import('./anexo/anexo.module').then(m => m.NgzorroAnexoModule)
      },
      {
        path: 'anexo-atendimento',
        loadChildren: () => import('./anexo-atendimento/anexo-atendimento.module').then(m => m.NgzorroAnexoAtendimentoModule)
      },
      {
        path: 'fornecedor',
        loadChildren: () => import('./fornecedor/fornecedor.module').then(m => m.NgzorroFornecedorModule)
      },
      {
        path: 'titulo',
        loadChildren: () => import('./titulo/titulo.module').then(m => m.NgzorroTituloModule)
      },
      {
        path: 'atividade',
        loadChildren: () => import('./atividade/atividade.module').then(m => m.NgzorroAtividadeModule)
      },
      {
        path: 'modelo-atividade',
        loadChildren: () => import('./modelo-atividade/modelo-atividade.module').then(m => m.NgzorroModeloAtividadeModule)
      },
      {
        path: 'produto',
        loadChildren: () => import('./produto/produto.module').then(m => m.NgzorroProdutoModule)
      },
      {
        path: 'categoria',
        loadChildren: () => import('./categoria/categoria.module').then(m => m.NgzorroCategoriaModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class NgzorroEntityModule {}
