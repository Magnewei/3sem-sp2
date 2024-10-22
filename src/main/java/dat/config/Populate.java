package dat.config;

import dat.daos.HaikuDAO;
import dat.dtos.HaikuDTO;
import dat.dtos.HaikuPartDTO;
import dat.entities.Haiku;

import jakarta.persistence.EntityManagerFactory;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

public class Populate {
    public static void main(String[] args) {

        Haiku haiku = createVanillaHaiku();
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        HaikuDAO dao = HaikuDAO.getInstance(emf);
        dao.create(new HaikuDTO(haiku));

        //System.out.println(haiku);
       /* EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        Set<Room> calRooms = getCalRooms();
        Set<Room> hilRooms = getHilRooms();

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Hotel california = new Hotel("Hotel California", "California", Hotel.HotelType.LUXURY);
            Hotel hilton = new Hotel("Hilton", "Copenhagen", Hotel.HotelType.STANDARD);
            california.setRooms(calRooms);
            hilton.setRooms(hilRooms);
            em.persist(california);
            em.persist(hilton);
            em.getTransaction().commit();
        }*/
    }

    @NotNull
    private static Haiku createVanillaHaiku() {
        List<HaikuPartDTO> haikuParts = new ArrayList<>();
        haikuParts.add(new HaikuPartDTO("Breath mingles, we burn", true));
        haikuParts.add(new HaikuPartDTO("Bodies move in sultry waves", false));
        haikuParts.add(new HaikuPartDTO("Night surrenders slow", true));

        HaikuDTO haiku = new HaikuDTO(0L, haikuParts, "Anonymous", null, null);
        Haiku haikuEntity = new Haiku(haiku);
        return haikuEntity;
    }

   /* @NotNull
    private static List<HaikuPart> getSpicyParts() {
        Room r111 = new Room(111, new BigDecimal(2520), Room.RoomType.SINGLE);
        Room r112 = new Room(112, new BigDecimal(2520), Room.RoomType.SINGLE);
        Room r113 = new Room(113, new BigDecimal(2520), Room.RoomType.SINGLE);
        Room r114 = new Room(114, new BigDecimal(2520), Room.RoomType.DOUBLE);
        Room r115 = new Room(115, new BigDecimal(3200), Room.RoomType.DOUBLE);
        Room r116 = new Room(116, new BigDecimal(4500), Room.RoomType.SUITE);

        Room[] roomArray = {r111, r112, r113, r114, r115, r116};
        return Set.of(roomArray);
    }*/
}
