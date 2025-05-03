"use client";
import Link from "next/link";

export default function RegisterPage() {
  return (
      <div className="min-h-screen bg-cyan-100 flex items-center justify-center p-4">
        <div className="w-full max-w-4xl bg-white rounded-lg shadow-lg overflow-hidden flex flex-col md:flex-row">
          <div className="w-full md:w-2/5 p-8 flex flex-col items-center justify-center bg-white">
            <div className="mb-12">
              <h1 className="text-5xl md:text-6xl font-bold text-gray-500 tracking-wider text-center">
                DIGITAL
                <br />
                EXAMS MANAGER
              </h1>
            </div>
          </div>
          <div className="w-full md:w-3/5 p-8 md:border-l border-gray-200">
            <div className="flex flex-col">
              <h2 className="text-xl font-bold text-black border-b-2 border-gray-200 pb-2 mb-6">
                CRÉER UN COMPTE
              </h2>

              <form action="login" method="get" className="w-full space-y-4">
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <div className="space-y-2">
                    <label htmlFor="firstName" className="text-base font-medium block">
                      Prénom
                    </label>
                    <input
                        id="firstName"
                        name="firstName"
                        type="text"
                        className="border border-gray-300 rounded-md w-full p-2"
                        placeholder="Entrez votre prénom"
                        required
                    />
                  </div>

                  <div className="space-y-2">
                    <label htmlFor="lastName" className="text-base font-medium block">
                      Nom
                    </label>
                    <input
                        id="lastName"
                        name="lastName"
                        type="text"
                        className="border border-gray-300 rounded-md w-full p-2"
                        placeholder="Entrez votre nom"
                        required
                    />
                  </div>
                </div>

                <div className="space-y-2">
                  <label htmlFor="email" className="text-base font-medium block">
                    Email
                  </label>
                  <div className="relative">
                    <div className="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" className="text-gray-400">
                        <rect width="20" height="16" x="2" y="4" rx="2"></rect>
                        <path d="m22 7-8.97 5.7a1.94 1.94 0 0 1-2.06 0L2 7"></path>
                      </svg>
                    </div>
                    <input
                        id="email"
                        name="email"
                        type="email"
                        className="pl-10 border border-gray-300 rounded-md w-full p-2"
                        placeholder="Entrez votre email"
                        required
                    />
                  </div>
                </div>

                <div className="space-y-2">
                  <label htmlFor="username" className="text-base font-medium block">
                    Nom d&apos;utilisateur
                  </label>
                  <div className="relative">
                    <div className="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" className="text-gray-400">
                        <path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2"></path>
                        <circle cx="12" cy="7" r="4"></circle>
                      </svg>
                    </div>
                    <input
                        id="username"
                        name="username"
                        type="text"
                        className="pl-10 border border-gray-300 rounded-md w-full p-2"
                        placeholder="Choisissez un nom d'utilisateur"
                        required
                    />
                  </div>
                </div>

                <div className="space-y-2">
                  <label htmlFor="password" className="text-base font-medium block">
                    Mot de passe
                  </label>
                  <div className="relative">
                    <div className="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" className="text-gray-400">
                        <rect width="18" height="11" x="3" y="11" rx="2" ry="2"></rect>
                        <path d="M7 11V7a5 5 0 0 1 10 0v4"></path>
                      </svg>
                    </div>
                    <input
                        id="password"
                        name="password"
                        type="password"
                        className="pl-10 border border-gray-300 rounded-md w-full p-2"
                        placeholder="Créez un mot de passe"
                        required
                    />
                  </div>
                </div>

                <div className="space-y-2">
                  <label htmlFor="confirmPassword" className="text-base font-medium block">
                    Confirmer le mot de passe
                  </label>
                  <div className="relative">
                    <div className="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" className="text-gray-400">
                        <rect width="18" height="11" x="3" y="11" rx="2" ry="2"></rect>
                        <path d="M7 11V7a5 5 0 0 1 10 0v4"></path>
                      </svg>
                    </div>
                    <input
                        id="confirmPassword"
                        name="confirmPassword"
                        type="password"
                        className="pl-10 border border-gray-300 rounded-md w-full p-2"
                        placeholder="Confirmez votre mot de passe"
                        required
                    />
                  </div>
                </div>

                <div className="space-y-2">
                  <label htmlFor="role" className="text-base font-medium block">
                    Rôle
                  </label>
                  <select
                      id="role"
                      name="role"
                      className="border border-gray-300 rounded-md w-full p-2"
                      required
                  >
                    <option value="">-- Sélectionnez un rôle --</option>
                    <option value="admin">Admin</option>
                    <option value="surveillant">Surveillant</option>
                  </select>
                </div>

                <div className="flex justify-between pt-4">
                  <Link
                      href="/login"
                      aria-label="login page"
                      className="border border-gray-300 text-gray-600 hover:bg-gray-50 px-6 py-2 rounded-md flex items-center gap-2"
                  >
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                      <path d="m12 19-7-7 7-7"></path>
                      <path d="M19 12H5"></path>
                    </svg>
                    Retour
                  </Link>

                  <button
                      type="submit"
                      aria-label="submit button"
                      className="bg-blue-600 hover:bg-blue-700 text-white px-6 py-2 rounded-md flex items-center gap-2"
                  >
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                      <polyline points="20 6 9 17 4 12"></polyline>
                    </svg>
                    S&apos;enregistrer
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
  );
}