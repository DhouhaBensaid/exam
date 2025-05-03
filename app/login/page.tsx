
import LoginForm from "@/components/login-form"
import Image from "next/image"
export const dynamic = "force-dynamic"

export default function LoginPage() {
  return (
    <div className="min-h-screen bg-cyan-100 flex items-center justify-center p-4">
      <div className="w-full max-w-4xl bg-white rounded-lg shadow-lg overflow-hidden flex flex-col md:flex-row">
        <div className="w-full md:w-2/5 p-8 flex flex-col items-center justify-center bg-white">
          <div className="mb-12">
            <h1 className="text-5xl md:text-6xl font-bold text-gray-500 tracking-wider text-center font-display">
              DIGITAL
              <br />
              EXAMS MANAGEMENT
            </h1>
          </div>
          <div className="relative w-full h-48 md:h-64">
            <Image
              src="https://www.medianet.tn/assets/public/images/jpg/MEDIANET/ms3.jpg"
              alt="Medianet"
              fill
              style={{ objectFit: "cover" }}
              className="rounded-lg"
              priority
            />
          </div>
        </div>
        <div className="w-full md:w-3/5 p-8 md:border-l border-gray-200">
          <div className="flex justify-center mb-6">
            <div className="flex items-center gap-4">
              <div className="relative w-16 h-16">
                <div className="absolute inset-0 bg-blue-500 rounded-full flex items-center justify-center">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                    strokeWidth="1"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    className="w-12 h-12 text-blue-200"
                  >
                    <circle cx="12" cy="12" r="10" fill="#1E88E5" />
                    <path d="M3 9l9-7 9 7" fill="#4CAF50" stroke="none" />
                    <path d="M3 9l9 3 9-3" fill="#81C784" stroke="none" />
                    <path d="M3 9v6l9 3 9-3V9" fill="none" stroke="#fff" />
                  </svg>
                </div>
              </div>
              <div className="relative w-20 h-20">
                <div className="absolute inset-0 flex items-center justify-center">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                    strokeWidth="1"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    className="w-20 h-20"
                  >
                    <path d="M12 2a5 5 0 0 1 5 5v2a5 5 0 0 1-10 0V7a5 5 0 0 1 5-5z" fill="#90CAF9" stroke="none" />
                    <path d="M12 12l-2-1-2 1v4l2 1 2-1 2 1 2-1v-4l-2-1-2 1z" fill="#BBDEFB" stroke="none" />
                    <path d="M6 10v4a6 6 0 0 0 12 0v-4" fill="#64B5F6" stroke="none" />
                    <rect x="8" y="10" width="8" height="12" rx="1" fill="#42A5F5" stroke="none" />
                    <path d="M10 15h4" stroke="#fff" />
                    <path d="M10 18h4" stroke="#fff" />
                  </svg>
                </div>
              </div>
            </div>
          </div>
          <LoginForm />
        </div>
      </div>
    </div>
  )
}
