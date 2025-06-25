package com.example.pontual.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.pontual.api.models.Delivery
import com.example.pontual.api.models.DeliveryRequest
import com.example.pontual.api.models.Driver
import com.example.pontual.api.models.Route
import com.example.pontual.repository.DeliveryRepository
import com.example.pontual.repository.DriverRepository
import com.example.pontual.repository.RouteRepository
import com.example.pontual.components.ValidationError
import com.example.pontual.components.SuccessMessage
import com.example.pontual.components.LoadingButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliveryFormScreen(
    deliveryId: Int? = null,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val deliveryRepository = remember { DeliveryRepository() }
    val routeRepository = remember { RouteRepository() }
    val driverRepository = remember { DriverRepository() }
    
    var selectedRouteId by remember { mutableStateOf<Int?>(null) }
    var selectedDriverId by remember { mutableStateOf<Int?>(null) }
    var notes by remember { mutableStateOf("") }
    
    var availableRoutes by remember { mutableStateOf<List<Route>>(emptyList()) }
    var availableDrivers by remember { mutableStateOf<List<Driver>>(emptyList()) }
    
    var isLoading by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var showSuccess by remember { mutableStateOf(false) }
    var successMessage by remember { mutableStateOf("") }
    var isEditing by remember { mutableStateOf(deliveryId != null) }

    fun loadData() {
        scope.launch {
            isLoading = true
            showError = false
            
            val routesResult = routeRepository.getRoutes()
            val driversResult = driverRepository.getDrivers()
            
            routesResult.fold(
                onSuccess = { routes ->
                    availableRoutes = routes.filter { it.isActive }
                },
                onFailure = { exception ->
                    showError = true
                    errorMessage = "Erro ao carregar rotas: ${exception.message}"
                }
            )
            
            driversResult.fold(
                onSuccess = { drivers ->
                    availableDrivers = drivers.filter { it.isActive }
                },
                onFailure = { exception ->
                    showError = true
                    errorMessage = "Erro ao carregar motoristas: ${exception.message}"
                }
            )
            
            if (deliveryId != null) {
                deliveryRepository.getDelivery(deliveryId).fold(
                    onSuccess = { delivery ->
                        selectedRouteId = delivery.routeId
                        selectedDriverId = delivery.driverId
                        notes = delivery.notes ?: ""
                    },
                    onFailure = { exception ->
                        showError = true
                        errorMessage = "Erro ao carregar entrega: ${exception.message}"
                    }
                )
            }
            
            isLoading = false
        }
    }

    fun saveDelivery() {
        if (selectedRouteId == null) {
            showError = true
            errorMessage = "Selecione uma rota"
            return
        }

        scope.launch {
            isLoading = true
            showError = false
            showSuccess = false
            
            val request = DeliveryRequest(
                routeId = selectedRouteId!!,
                driverId = selectedDriverId,
                notes = notes.trim().takeIf { it.isNotBlank() }
            )

            val result = if (isEditing) {
                deliveryRepository.updateDelivery(deliveryId!!, request)
            } else {
                deliveryRepository.createDelivery(request)
            }

            result.fold(
                onSuccess = {
                    showSuccess = true
                    successMessage = if (isEditing) "Entrega atualizada com sucesso!" else "Entrega criada com sucesso!"
                    kotlinx.coroutines.delay(1500)
                    onNavigateBack()
                },
                onFailure = { exception ->
                    showError = true
                    errorMessage = exception.message ?: "Erro ao salvar entrega"
                }
            )
            isLoading = false
        }
    }

    LaunchedEffect(Unit) {
        loadData()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditing) "Editar Entrega" else "Nova Entrega") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    IconButton(
                        onClick = { saveDelivery() },
                        enabled = !isLoading && selectedRouteId != null
                    ) {
                        Icon(Icons.Filled.Save, contentDescription = "Salvar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            if (showError) {
                ValidationError(message = errorMessage)
                Spacer(modifier = Modifier.height(16.dp))
            }

            if (showSuccess) {
                SuccessMessage(message = successMessage)
                Spacer(modifier = Modifier.height(16.dp))
            }

            Text(
                text = "Rota *",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            
            availableRoutes.forEach { route ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedRouteId == route.id,
                        onClick = { selectedRouteId = route.id }
                    )
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = route.name,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        if (route.description != null) {
                            Text(
                                text = route.description,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Text(
                            text = "${route.points.size} pontos de entrega",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Motorista (Opcional)",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            
            availableDrivers.forEach { driver ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedDriverId == driver.id,
                        onClick = { selectedDriverId = driver.id }
                    )
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = driver.name,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        if (driver.phone != null) {
                            Text(
                                text = driver.phone,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedDriverId == null,
                    onClick = { selectedDriverId = null }
                )
                Text(
                    text = "Sem motorista atribuído",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Observações") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )

            Spacer(modifier = Modifier.height(32.dp))

            LoadingButton(
                text = if (isEditing) "Atualizar" else "Criar",
                isLoading = isLoading,
                onClick = { saveDelivery() }
            )
        }
    }
} 