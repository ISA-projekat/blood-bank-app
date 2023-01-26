package com.bloodbank.bloodbankapp.hospitalintegration.urgentbloodtransfer;

import grpcService.Model;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class BloodBankBloodTransferService extends grpcService.UrgentBloodTransferGrpcServiceGrpc.UrgentBloodTransferGrpcServiceImplBase {

    // Setup for grpc service that exchanges urgent blood transfer messages, do not touch if you're not 100% sure what you're doing :)
    @Override
    public void transfer(Model.UrgentBloodTransferRequest request, StreamObserver<Model.UrgentBloodTransferResponse> responseObserver) {
        System.out.println(request.getBloodType() + " " + request.getAmount());

        Model.UrgentBloodTransferResponse response = Model.UrgentBloodTransferResponse.newBuilder()
                .setHasBlood(true).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
