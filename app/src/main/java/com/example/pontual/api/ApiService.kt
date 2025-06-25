package com.example.pontual.api

import com.example.pontual.api.models.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    // Autenticação
    @POST("user/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
    
    @POST("user/logout")
    suspend fun logout(): Response<Unit>
    
    @POST("user/refresh")
    suspend fun refreshToken(@Body refreshRequest: RefreshTokenRequest): Response<LoginResponse>
    
    // Usuários
    @GET("users")
    suspend fun getUsers(): Response<List<UserData>>
    
    @GET("users/profile")
    suspend fun getUserProfile(): Response<UserData>
    
    // Pontos de Entrega
    @GET("delivery-points")
    suspend fun getDeliveryPoints(): Response<List<DeliveryPoint>>
    
    @GET("delivery-points/{id}")
    suspend fun getDeliveryPoint(@Path("id") id: Int): Response<DeliveryPoint>
    
    @POST("delivery-points")
    suspend fun createDeliveryPoint(@Body request: DeliveryPointRequest): Response<DeliveryPoint>
    
    @PUT("delivery-points/{id}")
    suspend fun updateDeliveryPoint(
        @Path("id") id: Int,
        @Body request: DeliveryPointRequest
    ): Response<DeliveryPoint>
    
    @DELETE("delivery-points/{id}")
    suspend fun deleteDeliveryPoint(@Path("id") id: Int): Response<Unit>
    
    // Rotas
    @GET("routes")
    suspend fun getRoutes(): Response<List<Route>>
    
    @GET("routes/{id}")
    suspend fun getRoute(@Path("id") id: Int): Response<Route>
    
    @POST("routes")
    suspend fun createRoute(@Body request: RouteRequest): Response<Route>
    
    @PUT("routes/{id}")
    suspend fun updateRoute(
        @Path("id") id: Int,
        @Body request: RouteRequest
    ): Response<Route>
    
    @DELETE("routes/{id}")
    suspend fun deleteRoute(@Path("id") id: Int): Response<Unit>
    
    @POST("routes/assign")
    suspend fun assignRoute(@Body request: RouteAssignmentRequest): Response<Route>
    
    // Motoristas
    @GET("drivers")
    suspend fun getDrivers(): Response<List<Driver>>
    
    @GET("drivers/{id}")
    suspend fun getDriver(@Path("id") id: Int): Response<Driver>
    
    @POST("drivers")
    suspend fun createDriver(@Body request: DriverRequest): Response<Driver>
    
    @PUT("drivers/{id}")
    suspend fun updateDriver(
        @Path("id") id: Int,
        @Body request: DriverRequest
    ): Response<Driver>
    
    @DELETE("drivers/{id}")
    suspend fun deleteDriver(@Path("id") id: Int): Response<Unit>
    
    @GET("drivers/{driverId}/routes")
    suspend fun getDriverRoutes(@Path("driverId") driverId: Int): Response<List<Route>>
    
    // Entregas
    @GET("deliveries")
    suspend fun getDeliveries(
        @Query("status") status: String? = null,
        @Query("driverId") driverId: Int? = null,
        @Query("routeId") routeId: Int? = null
    ): Response<List<Delivery>>
    
    @GET("deliveries/{id}")
    suspend fun getDelivery(@Path("id") id: Int): Response<Delivery>
    
    @POST("deliveries")
    suspend fun createDelivery(@Body request: DeliveryRequest): Response<Delivery>
    
    @PUT("deliveries/{id}")
    suspend fun updateDelivery(
        @Path("id") id: Int,
        @Body request: DeliveryRequest
    ): Response<Delivery>
    
    @DELETE("deliveries/{id}")
    suspend fun deleteDelivery(@Path("id") id: Int): Response<Unit>
    
    @POST("deliveries/{id}/start")
    suspend fun startDelivery(@Path("id") id: Int): Response<Delivery>
    
    @POST("deliveries/{id}/complete")
    suspend fun completeDelivery(@Path("id") id: Int): Response<Delivery>
    
    @PUT("deliveries/{id}/status")
    suspend fun updateDeliveryStatus(
        @Path("id") id: Int,
        @Path("pointId") pointId: Int,
        @Query("status") status: String
    ): Response<Delivery>
} 