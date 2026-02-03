package com.myorg;

import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
// Imports de Red y ECS
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.ecs.Cluster;
import software.amazon.awscdk.services.ecs.ContainerImage;
import software.amazon.awscdk.services.ecs.AssetImageProps;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedFargateService;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedTaskImageOptions;
import java.util.Arrays;

public class DeploymentStack extends Stack {
    public DeploymentStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public DeploymentStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        // 1. Capa de Red: VPC con 2 Zonas de Disponibilidad
        Vpc vpc = Vpc.Builder.create(this, "TransferServiceVpc")
                .maxAzs(2)
                .build();

        // 2. Capa de Orquestación: Cluster de ECS
        Cluster cluster = Cluster.Builder.create(this, "TransferServiceCluster")
                .vpc(vpc)
                .build();

        // 3. Capa de Aplicación: Servicio Fargate con Balanceador de Carga
        ApplicationLoadBalancedFargateService.Builder.create(this, "TransferService")
                .cluster(cluster)           // El "cerebro" donde corre la app
                .cpu(512)                   // Recursos de CPU
                .memoryLimitMiB(1024)       // Recursos de RAM
                .taskImageOptions(
                        ApplicationLoadBalancedTaskImageOptions.builder()
                                // Configuramos la imagen Docker ignorando archivos bloqueados
                                .image(ContainerImage.fromAsset("..", AssetImageProps.builder()
                                        .exclude(Arrays.asList(".gradle", ".idea", "deployment", "build", ".git"))
                                        .build()))
                                .containerPort(8080) // El puerto de tu app Spring Boot
                                .build())
                .publicLoadBalancer(true)   // Para que sea accesible desde internet
                .build();
    }
}