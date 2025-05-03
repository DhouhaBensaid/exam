// Helper pour gérer l'authentification côté client
export const setToken = (token: string, remember: boolean = false) => {
    if (remember) {
        localStorage.setItem('token', token)
    } else {
        sessionStorage.setItem('token', token)
    }
}

export const getToken = () => {
    return localStorage.getItem('token') || sessionStorage.getItem('token')
}

export const setUserRole = (role: string) => {
    localStorage.setItem('userRole', role)
}

export const getUserRole = () => {
    return localStorage.getItem('userRole')
}

export const clearAuth = () => {
    localStorage.removeItem('token')
    sessionStorage.removeItem('token')
    localStorage.removeItem('userRole')
}