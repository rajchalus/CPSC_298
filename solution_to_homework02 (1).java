data Email : Type where
  Email : { domain : String , localPart : String } -> Email
    (forall x : { p : domain x = "example.com" } -> True)

data SecureString : Type where
  SecureString : { minLength : Int, charRules : List CharPattern } -> 
                 { value : String } -> SecureString
                 (forall x : { length (value x) >= minLength x & 
                             all (hasMatchingPattern (value x) (charRules x)) } -> True)

data HashedPassword : Type where
  HashedPassword : { algorithm : String, value : String } -> HashedPassword

login : { email : Email, password : SecureString } -> Bool
login { email, password } = ... (implementation with type-level checks)
