package com.example.pontual.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.pontual.api.models.Delivery
import com.example.pontual.api.models.DeliveryStatus
import com.example.pontual.repository.DeliveryRepository
import com.example.pontual.components.LoadingScreen
import com.example.pontual.components.ErrorScreen
import com.example.pontual.components.EmptyStateScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliveriesScreen(
    onNavigateToCreate: () -> Unit,
    onNavigateToEdit: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val repository = remember { DeliveryRepository() }
    
    var deliveries by remember { mutableStateOf<List<Delivery>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    fun loadDeliveries() {
        scope.launch {
            isLoading = true
            showError = false
            repository.getDeliveries().fold(
                onSuccess = { deliveriesList ->
                    deliveries = deliveriesList
                },
                onFailure = { exception ->
                    showError = true
                    errorMessage = exception.message ?: "Erro desconhecido"
                }
            )
            isLoading = false
        }
    }

    LaunchedEffect(Unit) {
        loadDeliveries()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Entregas") },
                actions = {
                    IconButton(onClick = onNavigateToCreate) {
                        Icon(Icons.Default.Add, contentDescription = "Adicionar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToCreate) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                isLoading -> {
                    LoadingScreen(message = "Carregando entregas...")
                }
                showError -> {
                    ErrorScreen(
                        message = errorMessage,
                        onRetry = { loadDeliveries() }
                    )
                }
                deliveries.isEmpty() -> {
                    EmptyStateScreen(
                        title = "Nenhuma entrega encontrada",
                        message = "Adicione a primeira entrega para começar"
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(deliveries) { delivery ->
                            DeliveryCard(
                                delivery = delivery,
                                onEdit = { onNavigateToEdit(delivery.id) },
                                onDelete = {
                                    scope.launch {
                                        repository.deleteDelivery(delivery.id).fold(
                                            onSuccess = { loadDeliveries() },
                                            onFailure = { exception ->
                                                showError = true
                                                errorMessage = exception.message ?: "Erro ao deletar"
                                            }
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliveryCard(
    delivery: Delivery,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Entrega #${delivery.id}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Rota: ${delivery.routeName}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    if (!delivery.driverName.isNullOrBlank()) {
                        Text(
                            text = "Motorista: ${delivery.driverName}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Text(
                        text = "Status: ${statusLabel(delivery.status)}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "Agendada: ${delivery.scheduledDate}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    if (!delivery.completedDate.isNullOrBlank()) {
                        Text(
                            text = "Concluída: ${delivery.completedDate}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    if (!delivery.notes.isNullOrBlank()) {
                        Text(
                            text = "Obs: ${delivery.notes}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                Column {
                    IconButton(onClick = onEdit) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar")
                    }
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(Icons.Default.Delete, contentDescription = "Deletar")
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AssistChip(
                    onClick = {},
                    label = {
                        Text(
                            text = statusLabel(delivery.status),
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    colors = when (delivery.status) {
                        DeliveryStatus.COMPLETED -> AssistChipDefaults.assistChipColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                        DeliveryStatus.IN_PROGRESS -> AssistChipDefaults.assistChipColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                        DeliveryStatus.PENDING -> AssistChipDefaults.assistChipColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
                        DeliveryStatus.FAILED, DeliveryStatus.CANCELLED -> AssistChipDefaults.assistChipColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                    }
                )
            }
        }
    }
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Confirmar exclusão") },
            text = { Text("Tem certeza que deseja excluir a entrega #${delivery.id}?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDelete()
                        showDeleteDialog = false
                    }
                ) {
                    Text("Excluir")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

private fun statusLabel(status: DeliveryStatus): String = when (status) {
    DeliveryStatus.COMPLETED -> "Concluída"
    DeliveryStatus.IN_PROGRESS -> "Em Andamento"
    DeliveryStatus.PENDING -> "Pendente"
    DeliveryStatus.FAILED -> "Falhou"
    DeliveryStatus.CANCELLED -> "Cancelada"
} 