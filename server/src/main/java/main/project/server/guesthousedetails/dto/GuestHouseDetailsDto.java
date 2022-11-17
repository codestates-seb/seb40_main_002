package main.project.server.guesthousedetails.dto;


import lombok.*;


public class GuestHouseDetailsDto {

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Post{

        private Boolean guestHouseParty;

        private Boolean guestHouseKitchen;

        private Boolean guestHouseWashing;

        private Boolean guestHouseOcean;

        private Boolean guestHouseTask;

        private Boolean guestHouseEssential;

        private Boolean guestHouseWifi;

        private Boolean guestHouseBoard;

        private Boolean guestHouseCook;

        private Boolean guestHouseParking;

    }


    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Put{

        private Boolean guestHouseParty;

        private Boolean guestHouseKitchen;

        private Boolean guestHouseWashing;

        private Boolean guestHouseOcean;

        private Boolean guestHouseTask;

        private Boolean guestHouseEssential;

        private Boolean guestHouseWifi;

        private Boolean guestHouseBoard;

        private Boolean guestHouseCook;

        private Boolean guestHouseParking;

    }


    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response{

        private Long guestHouseDetailsId;

        private Long guestHouseId;

        private Boolean guestHouseParty;

        private Boolean guestHouseKitchen;

        private Boolean guestHouseWashing;

        private Boolean guestHouseOcean;

        private Boolean guestHouseTask;

        private Boolean guestHouseEssential;

        private Boolean guestHouseWifi;

        private Boolean guestHouseBoard;

        private Boolean guestHouseCook;

        private Boolean guestHouseParking;

    }
}
