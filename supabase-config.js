const SUPABASE_URL =
  "https://ylquvyebfgsvipecljuu.supabase.co";

const SUPABASE_ANON_KEY =
  "sb_publishable_Ur8pHDUGsj8yTqo4cnEdmA_hoLwHYxT";

const supabaseClient =
  window.supabase.createClient(
    SUPABASE_URL,
    SUPABASE_ANON_KEY,
    {
      auth: {
        persistSession: true,
        autoRefreshToken: true,
        detectSessionInUrl: true,
        storage: window.localStorage
      }
    }
  );
