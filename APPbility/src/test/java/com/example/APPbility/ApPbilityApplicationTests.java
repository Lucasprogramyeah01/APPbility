package com.example.APPbility;

import com.example.APPbility.files.model.FileMetadata;
import com.example.APPbility.service.TalentoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.APPbility.dto.talento.EditTalentoCmd;
import com.example.APPbility.model.Talento;
import com.example.APPbility.repository.TalentoRepository;
import com.example.APPbility.files.service.StorageService;
import com.example.APPbility.user.model.User;
import com.example.APPbility.user.repository.UserRepository;
import com.example.APPbility.error.TalentoNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.*;
import org.springframework.web.multipart.MultipartFile;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ApPbilityApplicationTests {

	@Mock
	private TalentoRepository talentoRepository;

	@Mock
	private StorageService storageService;

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private TalentoService talentoService;

	private User mockUser;
	private EditTalentoCmd mockEditTalentoCmd;
	private Talento mockTalento;
	private MultipartFile mockFile;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		mockUser = new User();

		mockEditTalentoCmd = new EditTalentoCmd("Titulo", "Descripcion", List.of("imagen1.jpg"));

		mockTalento = new Talento(1L, "Titulo", "Descripcion", List.of("imagen1.jpg"), mockUser);

		mockFile = mock(MultipartFile.class);

		when(mockFile.getOriginalFilename()).thenReturn("imagen1.jpg");
		when(mockFile.getSize()).thenReturn(1024L); // Configura el tamaño del archivo

		FileMetadata mockFileMetadata = mock(FileMetadata.class);
		when(mockFileMetadata.getFilename()).thenReturn("imagen1.jpg");

		when(storageService.store(mockFile)).thenReturn(mockFileMetadata);

		when(userRepository.findListaTalentosByUsuarioID(mockUser.getId())).thenReturn(List.of());
	}

	@Test
	void testExistsTalentoByUsuarioId() {
		UUID usuarioId = UUID.randomUUID();
		when(talentoRepository.existsTalentoByUsuario_Id(usuarioId)).thenReturn(true);

		boolean result = talentoService.existsTalentoByUsuario_Id(usuarioId);

		assertTrue(result);
		verify(talentoRepository, times(1)).existsTalentoByUsuario_Id(usuarioId);
	}

	@Test
	void testExistsTalentoByUsuarioIdNoExiste() {
		UUID usuarioId = UUID.randomUUID();
		when(talentoRepository.existsTalentoByUsuario_Id(usuarioId)).thenReturn(false);

		boolean result = talentoService.existsTalentoByUsuario_Id(usuarioId);

		assertFalse(result);
		verify(talentoRepository, times(1)).existsTalentoByUsuario_Id(usuarioId);
	}

	@Test
	void testSaveTalentoConImagenes() {
		when(talentoRepository.save(any(Talento.class))).thenReturn(mockTalento);

		Talento result = talentoService.save(mockUser, mockEditTalentoCmd, mockFile);

		assertNotNull(result);
		assertEquals("Titulo", result.getTitulo());
		assertEquals(1, result.getListaImagenes().size());
		verify(talentoRepository, times(1)).save(any(Talento.class));
	}

	@Test
	void testSaveTalentoSinImagenes() {
		when(talentoRepository.save(any(Talento.class))).thenReturn(mockTalento);

		Talento result = talentoService.save(mockUser, mockEditTalentoCmd);

		assertNotNull(result);
		assertEquals("Titulo", result.getTitulo());
		assertEquals(0, result.getListaImagenes().size());
		verify(talentoRepository, times(1)).save(any(Talento.class));
	}

	@Test
	void testEditTalento() {
		when(talentoRepository.findById(1L)).thenReturn(Optional.of(mockTalento));

		Talento result = talentoService.edit(mockEditTalentoCmd, 1L, mockFile);

		assertNotNull(result);
		assertEquals("Titulo", result.getTitulo());
		assertTrue(result.getListaImagenes().contains("imagen1.jpg"));
		verify(talentoRepository, times(1)).save(any(Talento.class));
	}

	@Test
	void testEditTalentoNoEncontrado() {
		when(talentoRepository.findById(1L)).thenReturn(Optional.empty());

		TalentoNotFoundException exception = assertThrows(TalentoNotFoundException.class, () -> {
			talentoService.edit(mockEditTalentoCmd, 1L, mockFile);
		});

		assertEquals("No se ha encontrado ningún Talento con ID: 1.", exception.getMessage());
	}

}
