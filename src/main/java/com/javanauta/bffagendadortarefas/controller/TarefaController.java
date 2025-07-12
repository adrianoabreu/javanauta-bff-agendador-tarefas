package com.javanauta.bffagendadortarefas.controller;


import com.javanauta.bffagendadortarefas.business.TarefaService;
import com.javanauta.bffagendadortarefas.business.dto.in.TarefaDTORequest;
import com.javanauta.bffagendadortarefas.business.dto.out.TarefaDTOResponse;
import com.javanauta.bffagendadortarefas.business.enums.StatusNotificacaoEnum;
import com.javanauta.bffagendadortarefas.infrastructure.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
@Tag(name = "Tarefa", description = "Cadastra tarefas dos usuários")
@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
public class TarefaController {

    private final TarefaService tarefaService;

    @PostMapping
    @Operation(summary = "Salvar Tarefas de Usuários", description = "Cria um nova tarefa")
    @ApiResponse(responseCode = "200", description = "Tarefa salva com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<TarefaDTOResponse> gravarTarefas(@RequestBody TarefaDTORequest dto,
                                                           @RequestHeader(value = "Authorization", required = false) String token){
        return ResponseEntity.ok(tarefaService.gravarTarefa(token, dto));
    }

    @GetMapping("/eventos")
    @Operation(summary = "Busca tarefas por Período", description = "Busca tarefas cadastradas por período")
    @ApiResponse(responseCode = "200", description = "Tarefas encontradas")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    public ResponseEntity<List<TarefaDTOResponse>> buscaListaDeTarefasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal,
            @RequestHeader(name = "Authorization", required = false) String token){
            return ResponseEntity.ok(tarefaService.buscaTarefasAgendadasPorPeriodo(dataInicial,dataFinal,token));
    }

    @GetMapping
    @Operation(summary = "Busca Tarefas por email", description = "Busca tarefas por email")
    @ApiResponse(responseCode = "200", description = "Tarefas encontrada")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    @ApiResponse(responseCode = "403", description = "Email não encontrado")
    @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    public ResponseEntity<List<TarefaDTOResponse>> buscaTarefasPorEmail(@RequestHeader(name = "Authorization", required = false) String token) {
        return  ResponseEntity.ok(tarefaService.buscaTarefasPorEmail(token));
    }

    @DeleteMapping
    @Operation(summary = "Deleta Tarefas por id", description = "Deleta tarefas cadastradas por Id")
    @ApiResponse(responseCode = "200", description = "Tarefa deletada")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    @ApiResponse(responseCode = "403", description = "Tarefa id não encontrada")
    @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    public ResponseEntity<Void> deletaTarefaPorId(@RequestParam("id") String id,
                                                  @RequestHeader(name = "Authorization", required = false) String token){
        tarefaService.deletaTarefaPorId(id,token);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    @Operation(summary = "Altera status de tarefas", description = "Altera status das tarefas cadastradas")
    @ApiResponse(responseCode = "200", description = "Status da Tarefa alterado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    @ApiResponse(responseCode = "403", description = "Tarefa id não encontrada")
    @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    public ResponseEntity<TarefaDTOResponse> alteraStatusNotificacao(@RequestParam("status") StatusNotificacaoEnum status,
                                                             @RequestParam("id") String id,
                                                             @RequestHeader(name = "Authorization", required = false) String token){
        return  ResponseEntity.ok(tarefaService.alteraStatus(status, id,token));
    }

    @PutMapping
    @Operation(summary = "Altera dados de Tarefas", description = "Altera dados de Tarefas cadastradas")
    @ApiResponse(responseCode = "200", description = "Tarefas alteradas")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    @ApiResponse(responseCode = "403", description = "Tarefa id não encontrada")
    @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    public ResponseEntity<TarefaDTOResponse> updateTarefas(@RequestBody TarefaDTORequest dto,
                                                   @RequestParam("id") String id,
                                                   @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(tarefaService.updateTarefas(dto, id,token));
    }

}















