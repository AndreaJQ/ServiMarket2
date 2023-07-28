@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UsuarioRepository usuarioRepository;

    public AdminController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/usuarios")
    public String mostrarTodosLosUsuarios(Model model) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        model.addAttribute("usuarios", usuarios);
        return "lista_usuarios";
    }

    @PostMapping("/usuarios/cambiar-rol/{id}")
    public String cambiarRolUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario != null) {
            // Cambiar el rol del usuario entre "Cliente" y "Proveedor"
            String nuevoRol = usuario.getRol().equals("Cliente") ? "Proveedor" : "Cliente";
            usuario.setRol(nuevoRol);
            usuarioRepository.save(usuario);
        }
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/usuarios/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
        return "redirect:/admin/usuarios";
    }
}
