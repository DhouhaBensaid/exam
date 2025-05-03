import axios from "axios"

const API_URL = process.env.NEXT_PUBLIC_API_URL || "http://localhost:8080/api"

// Créer une instance axios avec la configuration de base
const api = axios.create({
  baseURL: API_URL,
  headers: {
    "Content-Type": "application/json",
  },
})

// Intercepteur pour ajouter le token JWT à chaque requête
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token")
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  },
)

// Intercepteur pour gérer les erreurs d'authentification
api.interceptors.response.use(
  (response) => {
    return response
  },
  (error) => {
    if (error.response && error.response.status === 401) {
      // Rediriger vers la page de connexion si non authentifié
      localStorage.removeItem("token")
      window.location.href = "/login"
    }
    return Promise.reject(error)
  },
)

// Services d'authentification
export const authService = {
  login: async (username: string, password: string) => {
    const response = await api.post("api/auth/login", { username, password })
    if (response.data.token) {
      localStorage.setItem("token", response.data.token)
    }
    return response.data
  },

  register: async (userData: any) => {
    return api.post("/auth/register", userData)
  },

  logout: () => {
    localStorage.removeItem("token")
  },

  isAuthenticated: () => {
    return !!localStorage.getItem("token")
  },
}

// Services pour les spécialités
export const specialiteService = {
  getAll: async () => {
    const response = await api.get("/specialites")
    return response.data
  },

  getById: async (id: number) => {
    const response = await api.get(`/specialites/${id}`)
    return response.data
  },

  create: async (specialite: any) => {
    const response = await api.post("/specialites", specialite)
    return response.data
  },

  update: async (id: number, specialite: any) => {
    const response = await api.put(`/specialites/${id}`, specialite)
    return response.data
  },

  delete: async (id: number) => {
    return api.delete(`/specialites/${id}`)
  },
}

// Services pour les niveaux
export const niveauService = {
  getAll: async () => {
    const response = await api.get("/niveaux")
    return response.data
  },

  getBySpecialite: async (specialiteId: number) => {
    const response = await api.get(`/niveaux/specialite/${specialiteId}`)
    return response.data
  },

  getById: async (id: number) => {
    const response = await api.get(`/niveaux/${id}`)
    return response.data
  },
}

// Services pour les examens
export const examenService = {
  getAll: async () => {
    const response = await api.get("/examens")
    return response.data
  },

  getBySpecialite: async (specialiteId: number, anneeAcademique: string) => {
    const response = await api.get(`/examens/specialite/${specialiteId}?anneeAcademique=${anneeAcademique}`)
    return response.data
  },

  getByNiveau: async (niveauId: number, anneeAcademique: string) => {
    const response = await api.get(`/examens/niveau/${niveauId}?anneeAcademique=${anneeAcademique}`)
    return response.data
  },

  getBySalle: async (salleId: number) => {
    const response = await api.get(`/examens/salle/${salleId}`)
    return response.data
  },
}

// Services pour les salles
export const salleService = {
  getAll: async () => {
    const response = await api.get("/salles")
    return response.data
  },

  getBySpecialite: async (specialiteId: number) => {
    const response = await api.get(`/salles/specialite/${specialiteId}`)
    return response.data
  },

  getDisponibles: async (date: string, heureDebut: string, heureFin: string) => {
    const response = await api.get(`/salles/disponibles?date=${date}&heureDebut=${heureDebut}&heureFin=${heureFin}`)
    return response.data
  },
}

// Services pour les étudiants
export const etudiantService = {
  getAll: async () => {
    const response = await api.get("/etudiants")
    return response.data
  },

  getByNiveau: async (niveauId: number) => {
    const response = await api.get(`/etudiants/niveau/${niveauId}`)
    return response.data
  },

  getByExamenAndSalle: async (examenId: number, salleId: number) => {
    const response = await api.get(`/etudiants/examen/${examenId}/salle/${salleId}`)
    return response.data
  },

  search: async (term: string) => {
    const response = await api.get(`/etudiants/search?term=${term}`)
    return response.data
  },
}

export default api
